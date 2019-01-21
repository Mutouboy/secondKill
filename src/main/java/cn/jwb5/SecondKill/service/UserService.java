package cn.jwb5.SecondKill.service;

import cn.jwb5.SecondKill.VO.LoginVo;
import cn.jwb5.SecondKill.constant.CodeMsg;
import cn.jwb5.SecondKill.constant.ErrorMsg;
import cn.jwb5.SecondKill.constant.UserConstant;
import cn.jwb5.SecondKill.dao.UserDao;
import cn.jwb5.SecondKill.exception.ServiceException;
import cn.jwb5.SecondKill.model.User;
import cn.jwb5.SecondKill.redis.HtmlKey;
import cn.jwb5.SecondKill.redis.RedisService;
import cn.jwb5.SecondKill.redis.UserKey;
import cn.jwb5.SecondKill.utils.MD5Utils;
import cn.jwb5.SecondKill.utils.StringUtils;
import cn.jwb5.SecondKill.utils.UUIDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by jiangwenbin on 2019/1/7.
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    RedisService redisService;


    public User getByToken(String token,HttpServletResponse response){
        if (StringUtils.isEmpty(token)){
            return null;
        }
        User user =  redisService.get(UserKey.getByToken,token, User.class);
        if (user != null){
            addCookie(token,user,response);
        }
        return user;
    }


    public User getById(String id){
        User user = redisService.get(UserKey.getById,""+id,User.class);
        if (user != null){
            return user;
        }

        user = userDao.getById(id);
        if (user != null){
            redisService.set(UserKey.getById,""+id,user);
        }
        return user;
    }

    public User updatePassword(String id,String token,String newPassword){
        User u = getById(id);
        if (u == null){
            throw new ServiceException(ErrorMsg.NO_USER);
        }
        User user = new User();
        user.setId(u.getId());
        user.setPassword(MD5Utils.formPassToDBPass(newPassword,u.getSalt()));
        userDao.updatePassword(user);

        u.setPassword(user.getPassword());
        redisService.set(UserKey.getByToken,token,u);
        redisService.set(UserKey.getById,""+id,u);

        return u;
    }


    public boolean login(LoginVo vo, HttpServletResponse response){
        if (vo == null){
            throw new ServiceException(ErrorMsg.NO_USER);
        }
        String id = vo.getMobile();
        String formPass = vo.getPassword();
        User user = getById(id);
        if (user == null){
            throw new ServiceException(ErrorMsg.NO_USER);
        }

        String dbPass = MD5Utils.formPassToDBPass(formPass,user.getSalt());
        if (!user.getPassword().equals(dbPass)){
            throw new ServiceException(ErrorMsg.PASSWORD_ERROR);
        }


        String token = UUIDUtil.uuid();
        addCookie(token,user,response);
        return true;
    }

    /**
     *
     * @param token
     * @param user
     * @param response
     *
     * UserConstant.USER_COOKIE_TOKEN 为客户端存储token的key，服务端根据此常量获取前端参数
     * UserKey.getByToken为redis中存储token的前缀和过期时间，访问redis时使用该值做key
     */
    private void addCookie(String token,User user,HttpServletResponse response){
        redisService.set(UserKey.getByToken,token,user);
        Cookie cookie = new Cookie(UserConstant.USER_COOKIE_TOKEN,token);
        cookie.setMaxAge(UserKey.getByToken.expireTime());
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
