package cn.jwb5.SecondKill.VO;

import cn.jwb5.SecondKill.validator.IsMobile;

import javax.validation.constraints.NotNull;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
public class LoginVo {

    @NotNull
    @IsMobile
    private String mobile;

    @NotNull
    private String password;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "LoginVo{" +
                "mobile='" + mobile + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
