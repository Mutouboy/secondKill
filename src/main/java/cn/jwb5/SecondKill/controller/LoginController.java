package cn.jwb5.SecondKill.controller;

import cn.jwb5.SecondKill.VO.LoginVo;
import cn.jwb5.SecondKill.constant.CodeMsg;
import cn.jwb5.SecondKill.constant.UserConstant;
import cn.jwb5.SecondKill.result.Result;
import cn.jwb5.SecondKill.service.UserService;
import cn.jwb5.SecondKill.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
@Controller
@RequestMapping("/login/*")
public class LoginController {

    @Autowired
    private UserService userService;

    @RequestMapping("do_login")
    @ResponseBody
    public Result doLogin(@Valid LoginVo vo,
                          HttpServletResponse response){
        userService.login(vo,response);
        return Result.success(true);
    }

    @RequestMapping("to_login")
    public String toLogin(){
        return "login";
    }
}
