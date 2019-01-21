package cn.jwb5.SecondKill.controller;

import cn.jwb5.SecondKill.model.User;
import cn.jwb5.SecondKill.redis.RedisService;
import cn.jwb5.SecondKill.redis.UserKey;
import cn.jwb5.SecondKill.service.UserService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by jiangwenbin on 2019/1/7.
 */
@Controller
@RequestMapping("/demo")
public class SimpleController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("thyleaf")
    public String thyleaf(Model model){
        model.addAttribute("name","jwb");
        return "hello";
    }

//    @RequestMapping("db/get")
//    public String dbGet(@RequestParam("id") int id,
//            Model model){
//        User user = userService.getById(id);
//        model.addAttribute("user",user);
//        return "hello";
//    }
//
//    @RequestMapping("tx")
//    @Transactional
//    public void tx(){
//        userService.insert(2,"jwb2");
//        userService.insert(1,"jwb");
//    }

    @RequestMapping("redis/get")
    @ResponseBody
    public String redisGet(@RequestParam("K") String K){
        String value = redisService.get(UserKey.getById,K,String.class);
        return value;
    }

    @RequestMapping("redis/set")
    @ResponseBody
    public String redisGet(@RequestParam("K") String K,
                           @RequestParam("V") String V){
        boolean b = redisService.set(UserKey.getById,K,V);
        if (b){
            return "ok";
        }else {
            return "false";
        }
    }

    @RequestMapping("redis/incr")
    @ResponseBody
    public String redisIncr(@RequestParam("K") String K){
        return String.valueOf(redisService.incr(UserKey.getById,K));
    }

    @RequestMapping("redis/decr")
    @ResponseBody
    public String redisDecr(@RequestParam("K") String K){
        return String.valueOf(redisService.decr(UserKey.getById,K));
    }

    @RequestMapping("redis/del")
    @ResponseBody
    public String redisDel(@RequestParam("K") String K){
        return String.valueOf(redisService.del(UserKey.getById,K));
    }

    @RequestMapping("redis/exit")
    @ResponseBody
    public String redisExit(@RequestParam("K") String K){
        return String.valueOf(redisService.exit(UserKey.getById,K));
    }

}
