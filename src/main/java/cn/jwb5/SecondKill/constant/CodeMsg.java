package cn.jwb5.SecondKill.constant;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
public class CodeMsg {
    private int code;
    private String msg;

    public CodeMsg(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public CodeMsg exceptionError(Object...args){
        int code = this.code;
        String s = String.format(this.msg,args);
        return new CodeMsg(code,s);
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
