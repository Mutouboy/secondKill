package cn.jwb5.SecondKill.result;

import cn.jwb5.SecondKill.constant.CodeMsg;
import cn.jwb5.SecondKill.constant.ErrorMsg;

/**
 * Created by jiangwenbin on 2019/1/9.
 */
public class Result<T> {

    boolean isSuccess = true;
    private int code;
    private String msg;
    private CodeMsg codeMsg;
    private T data;


    private Result(T data) {
        this.data = data;
    }

    private Result(boolean isSuccess, CodeMsg codeMsg) {
        this.isSuccess = isSuccess;
        this.codeMsg = codeMsg;
        msg = codeMsg.getMsg();
        code= codeMsg.getCode();
    }

    private Result(boolean isSuccess, CodeMsg codeMsg, T data) {
        this.isSuccess = isSuccess;
        this.codeMsg = codeMsg;
        msg = codeMsg.getMsg();
        code= codeMsg.getCode();
        this.data = data;
    }


    public static <T> Result success(T data){
        return new Result(true, ErrorMsg.SUCCESS,data);
    }

    public static <T> Result setError(CodeMsg codeMsg){
        return new Result(false,codeMsg);
    }

    public static <T> Result setError(CodeMsg codeMsg,T data){
        return new Result(false,codeMsg,data);
    }


    public boolean isSuccess() {
        return isSuccess;
    }

    public String getMsg() {
        return msg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }

    public T getData() {
        return data;
    }

    public int getCode() {
        return code;
    }
}
