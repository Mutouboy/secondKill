package cn.jwb5.SecondKill.exception;

import cn.jwb5.SecondKill.constant.CodeMsg;

/**
 * Created by jiangwenbin on 2019/1/9.
 */
public class ServiceException extends RuntimeException {


    private CodeMsg codeMsg;

    public ServiceException(CodeMsg codeMsg){
        super(codeMsg.getMsg());
        this.codeMsg = codeMsg;
    }

    public CodeMsg getCodeMsg() {
        return codeMsg;
    }
}
