package cn.jwb5.SecondKill.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
public class ValidatorUtils {

    private static final Pattern patten = Pattern.compile("1\\d{10}");

    public static boolean isMobile(String mobile){
        if (StringUtils.isEmpty(mobile)){
            return false;
        }
        Matcher matcher = patten.matcher(mobile);
        return matcher.matches();
    }

}
