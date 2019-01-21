package cn.jwb5.SecondKill.webconfig;

import cn.jwb5.SecondKill.constant.UserConstant;
import cn.jwb5.SecondKill.model.User;
import cn.jwb5.SecondKill.service.UserService;
import cn.jwb5.SecondKill.utils.StringUtils;
import org.codehaus.groovy.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jiangwenbin on 2019/1/9.
 */
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver {


    @Autowired
    UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        Class<?> clazz = methodParameter.getParameterType();
        return clazz == User.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

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
