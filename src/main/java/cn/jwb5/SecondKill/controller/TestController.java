package cn.jwb5.SecondKill.controller;

import cn.jwb5.SecondKill.constant.ErrorMsg;
import cn.jwb5.SecondKill.result.Result;
import cn.jwb5.SecondKill.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jiangwenbin on 2019/1/13.
 */
@Controller
@RequestMapping("/test/*")
public class TestController {

    @Autowired
    TestService testService;

    @RequestMapping("create")
    @ResponseBody
    public Result createUser(){
        boolean t = testService.createUser();
        if (t){
            return Result.setError(ErrorMsg.SUCCESS);
        }
        else {
            return Result.setError(ErrorMsg.SYSTEM_ERROR);
        }
    }

    @RequestMapping("token")
    @ResponseBody
    public Result createToken(){
        testService.createToken();
            return Result.setError(ErrorMsg.SUCCESS);
    }
}
