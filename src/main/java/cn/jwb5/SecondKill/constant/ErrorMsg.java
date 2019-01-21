package cn.jwb5.SecondKill.constant;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
public interface ErrorMsg {

    CodeMsg SYSTEM_ERROR = new CodeMsg(100,"系统错误");
    CodeMsg SUCCESS = new CodeMsg(999,"成功");
    CodeMsg EXCEPTION_ERROR = new CodeMsg(99,"异常错误:%s");


    CodeMsg PASSWORD_ERROR = new CodeMsg(101,"密码错误");
    CodeMsg PASSWORD_EMPTY = new CodeMsg(102,"密码为空");
    CodeMsg NO_USER = new CodeMsg(103,"用户为空");
    CodeMsg ERROR_VARIBLE = new CodeMsg(104,"参数错误");

    CodeMsg NO_LOGIN = new CodeMsg(301,"请登录");
    CodeMsg REPEAT = new CodeMsg(302,"重复秒杀");
    CodeMsg NO_GOODS = new CodeMsg(303,"库存不足");
}
