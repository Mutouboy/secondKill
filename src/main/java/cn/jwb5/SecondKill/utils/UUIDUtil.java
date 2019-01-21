package cn.jwb5.SecondKill.utils;

import java.util.UUID;

/**
 * Created by jiangwenbin on 2019/1/9.
 */
public class UUIDUtil {

    public static String uuid(){
        return UUID.randomUUID().toString().replace("-","");
    }
}
