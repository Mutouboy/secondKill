package cn.jwb5.SecondKill.utils;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
public class StringUtils {
    public static boolean isEmpty(String str){
        if (str == null || str.length()<=0){
            return true;
        }
        return false;
    }
}
