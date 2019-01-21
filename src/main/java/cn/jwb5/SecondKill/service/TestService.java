package cn.jwb5.SecondKill.service;

import cn.jwb5.SecondKill.dao.UserDao;
import cn.jwb5.SecondKill.model.User;
import cn.jwb5.SecondKill.redis.RedisService;
import cn.jwb5.SecondKill.redis.UserKey;
import cn.jwb5.SecondKill.utils.UserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by jiangwenbin on 2019/1/13.
 */
@Service
public class TestService {

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;


    public boolean createUser(){
        //生成
        List<User> users = UserUtil.createUser();
        //insert db
        for (User user : users){
            userDao.insertUser(user);
        }
        return true;
    }

    public void createToken(){
        List<User> users = UserUtil.createUser();
        int i=0;
        for (User user : users){
            String key = String.valueOf(18000000000L+i);
            redisService.set(UserKey.getByToken,key,user);
            i++;
        }

    }
}
