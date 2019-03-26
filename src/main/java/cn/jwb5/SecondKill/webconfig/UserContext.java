package cn.jwb5.SecondKill.webconfig;

import cn.jwb5.SecondKill.model.User;


/**
 * Created by jiangwenbin on 2019/1/27.
 */
public class UserContext {

    private static ThreadLocal<User> userContext = new ThreadLocal<User>();

    public static void setUser(User user){
        userContext.set(user);
    }

    public static User getUser(){
        return userContext.get();
    }

}
