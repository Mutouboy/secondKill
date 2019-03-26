package cn.jwb5.SecondKill.intercepter;

import cn.jwb5.SecondKill.constant.CodeMsg;
import cn.jwb5.SecondKill.constant.ErrorMsg;
import cn.jwb5.SecondKill.constant.UserConstant;
import cn.jwb5.SecondKill.model.User;
import cn.jwb5.SecondKill.result.Result;
import cn.jwb5.SecondKill.service.UserService;
import cn.jwb5.SecondKill.utils.StringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * Created by jiangwenbin on 2019/1/27.
 */
@Service
public class LoginIntercepter extends HandlerInterceptorAdapter{

    @Autowired
    UserService userService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod)handler;
            NeedLogin nl = hm.getMethodAnnotation(NeedLogin.class);
            if (nl == null){
                return true;
            }
            boolean value = nl.value();
            User user = getUser(request,response);
            if (value && user==null){
                render(response,ErrorMsg.NO_USER);
                return false;
            }
        }
        return true;
    }

    private void render(HttpServletResponse response,CodeMsg cm) throws Exception{
        response.setContentType("application/json;charset=utf-8");
        OutputStream out = response.getOutputStream();
        String s = JSON.toJSONString(Result.setError(cm));
        out.write(s.getBytes("utf-8"));
        out.flush();
        out.close();
    }

    private User getUser(HttpServletRequest request, HttpServletResponse response){
        String rtk = request.getParameter(UserConstant.USER_COOKIE_TOKEN);
        String ctk = getCookieValue(request,UserConstant.USER_COOKIE_TOKEN);
        if (StringUtils.isEmpty(rtk) && StringUtils.isEmpty(ctk)){
            return null;
        }
        String token = StringUtils.isEmpty(rtk) ? ctk : rtk;
        return userService.getByToken(token,response);
    }

    private String getCookieValue(HttpServletRequest request, String cookiName){
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) {
            return null;
        }
        for (Cookie c : cookies){
            if (c.getName().equals(cookiName)){
                return c.getValue();
            }
        }
        return null;
    }

}
