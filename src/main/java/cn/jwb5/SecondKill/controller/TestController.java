package cn.jwb5.SecondKill.controller;

import cn.jwb5.SecondKill.constant.ErrorMsg;
import cn.jwb5.SecondKill.rabbitmq.MQSender;
import cn.jwb5.SecondKill.result.Result;
import cn.jwb5.SecondKill.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jiangwenbin on 2019/1/13.
 */
@Controller
@RequestMapping("/test/*")
public class TestController {

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    TestService testService;

    @Autowired
    MQSender mqSender;


    @RequestMapping("reset")
    @ResponseBody
    public Result reset(){
        boolean t = testService.reset();
        if (t){
            return Result.setError(ErrorMsg.SUCCESS);
        }
        else {
            return Result.setError(ErrorMsg.SYSTEM_ERROR);
        }
    }


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


    @RequestMapping("mqd")
    @ResponseBody
    public String mqDirect(@RequestParam("msg")String msg){
        mqSender.sendMsg(msg);
        log.info("controller "+msg);
        return msg;
    }

    @RequestMapping("mqt")
    @ResponseBody
    public String mqTopic(@RequestParam("msg")String msg){
        mqSender.sendMsg2Top(msg);
        log.info("controller "+msg);
        return msg;
    }

    @RequestMapping("mqf")
    @ResponseBody
    public String mqFanout(@RequestParam("msg")String msg){
        mqSender.sendMsg2Fanout(msg);
        log.info("controller "+msg);
        return msg;
    }

    @RequestMapping("mqh")
    @ResponseBody
    public String mqHeaders(@RequestParam("msg")String msg){
        mqSender.sendMsg2Header(msg);
        log.info("controller "+msg);
        return msg;
    }



}
