package cn.jwb5.SecondKill.exception;

import cn.jwb5.SecondKill.constant.CodeMsg;
import cn.jwb5.SecondKill.constant.ErrorMsg;
import cn.jwb5.SecondKill.result.Result;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by jiangwenbin on 2019/1/9.
 */
@ControllerAdvice
@ResponseBody
public class ServiceExceptionHandle {

    @ExceptionHandler(value = BindException.class)
    public Result bindExceptionHandler(HttpServletRequest request,BindException e){
        List<ObjectError> errors =e.getAllErrors();
        ObjectError error = errors.get(0);
        String msg  = error.getDefaultMessage();
        return Result.setError(ErrorMsg.EXCEPTION_ERROR.exceptionError(msg));
    }


    @ExceptionHandler(value = ServiceException.class)
    public Result serviceExceptionHandler(HttpServletRequest request,ServiceException e){
        return Result.setError(e.getCodeMsg());
    }
}
