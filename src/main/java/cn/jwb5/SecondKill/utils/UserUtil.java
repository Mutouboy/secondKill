package cn.jwb5.SecondKill.utils;

import cn.jwb5.SecondKill.model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jiangwenbin on 2019/1/13.
 */
public class UserUtil {

    public static List<User> createUser(){
        List<User> list = new ArrayList<>(5000);
        for (int i=0;i<5000;i++){
            User user = new User();
            user.setId(String.valueOf(18000000000L+i));
            user.setLoginCount(1);
            user.setNickname("test"+i);
            user.setPassword(MD5Utils.inputPassToDbPass("123456","1qaz2wsx"));
            user.setSalt("1qaz2wsx");
            user.setRegisterDate(new Date());
            list.add(user);
        }
        return list;

    }
}
