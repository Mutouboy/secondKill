package cn.jwb5.SecondKill.utils;

import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
public class MD5Utils {

    public static String md5(String src){
        return DigestUtils.md5Hex(src);
    }

    private static String salt = "suhdfouwhf";

    public static String inputPassToFormPass(String inputPass) {
         String str = salt+inputPass;
        return md5(str);
    }

    public static String formPassToDBPass(String formPass, String salt) {
        String str = salt+formPass;
        return md5(str);
    }

    public static String inputPassToDbPass(String inputPass, String saltDB) {
        String formPass = inputPassToFormPass(inputPass);
        String dbPass = formPassToDBPass(formPass, saltDB);
        return dbPass;
    }

    public static void main(String[] args) {
        //6b17ce83c68ad8808953dc87affbb324
        System.out.println(inputPassToFormPass("123456"));
        System.out.println(formPassToDBPass("6b17ce83c68ad8808953dc87affbb324","1qaz2wsx"));
        System.out.println(inputPassToDbPass("123456","1qaz2wsx"));
    }


}
