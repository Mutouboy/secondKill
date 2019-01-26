package cn.jwb5.SecondKill.redis;

import cn.jwb5.SecondKill.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
@Service
public class RedisService {

    @Autowired
    JedisPool jedisPool;


    public <T> T get(KeyPrefix prefix,String key,Class<T> clazz ){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String realKey = prefix.getPrefix()+key;
            String str = jedis.get(realKey);
            T t = string2Bean(str,clazz);
            return t;
        }finally {
            return2Pool(jedis);
        }

    }

    public  <T> T string2Bean(String str,Class<T> clazz){
        if (StringUtils.isEmpty(str) || clazz==null){
            return null;
        }
        if (clazz == int.class || clazz == Integer.class){
            return (T)Integer.valueOf(str);
        }
        if (clazz == long.class || clazz == Long.class){
            return (T)Long.valueOf(str);
        }
        if (clazz == String.class){
            return (T)str;
        }

        return JSON.toJavaObject(JSON.parseObject(str),clazz);
    }

    private void return2Pool(Jedis jedis){
        if (jedis!=null){
            jedis.close();
        }
    }


    public <T> boolean set(KeyPrefix prefix,String key,T value ){
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            String str = bean2String(value);

            if (StringUtils.isEmpty(str)){
                return false;
            }

            String realKey = prefix.getPrefix()+key;
            int time = prefix.expireTime();
            if (time<=0){
                jedis.set(realKey,str);
            }else {
                jedis.setex(realKey,time,str);
            }
            return true;
        }finally {
            return2Pool(jedis);
        }

    }

    public <T> String bean2String(T value){
        if (value == null){
            return null;
        }
        Class<?> clazz = value.getClass();
        if (clazz == int.class || clazz == Integer.class){
            return ""+value;
        }
        if (clazz == long.class || clazz == Long.class){
            return ""+value;
        }
        if (clazz == String.class){
            return (String)value;
        }

            return JSON.toJSONString(value);
    }

    public boolean exit(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis=jedisPool.getResource();
            String realKey = prefix.getPrefix()+key;
            return jedis.exists(realKey);
        }finally {
            return2Pool(jedis);
        }
    }

    public Long incr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis=jedisPool.getResource();
            String realKey = prefix.getPrefix()+key;
            return jedis.incr(realKey);
        }finally {
            return2Pool(jedis);
        }
    }

    public Long decr(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis=jedisPool.getResource();
            String realKey = prefix.getPrefix()+key;
            return jedis.decr(realKey);
        }finally {
            return2Pool(jedis);
        }
    }

    public Long del(KeyPrefix prefix,String key){
        Jedis jedis = null;
        try {
            jedis=jedisPool.getResource();
            String realKey = prefix.getPrefix()+key;
            return jedis.del(realKey);
        }finally {
            return2Pool(jedis);
        }
    }

}
