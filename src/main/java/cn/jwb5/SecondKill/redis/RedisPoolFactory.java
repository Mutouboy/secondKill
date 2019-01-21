package cn.jwb5.SecondKill.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * Created by jiangwenbin on 2019/1/8.
 *
 * 从RedisService中拿出来，否则会形成循环依赖
 * 因为RedisService注入pool，而pool的创建需要RedisService
 */
@Service
public class RedisPoolFactory {

    @Autowired
    RedisCfg redisCfg;


    @Bean
    public JedisPool jedisPoolFactory(){
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        poolConfig.setMaxIdle(redisCfg.getPoolMaxIdle());
        poolConfig.setMaxTotal(redisCfg.getPoolMaxTotal());
        poolConfig.setMaxWaitMillis(redisCfg.getPoolMaxWait()*1000);

        JedisPool jedisPool = new JedisPool(
                poolConfig,
                redisCfg.getHost(),
                redisCfg.getPort(),
                redisCfg.getTimeout()*1000,
                redisCfg.getPassword());
        return jedisPool;
    }
}
