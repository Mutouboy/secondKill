package cn.jwb5.SecondKill.VO;

import cn.jwb5.SecondKill.model.User;

/**
 * Created by jiangwenbin on 2019/1/18.
 */
public class GoodsDetailVo {
    private int miaoshaStatus;
    private int remainTime;
    private GoodsVo good;
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemainTime() {
        return remainTime;
    }

    public void setRemainTime(int remainTime) {
        this.remainTime = remainTime;
    }

    public GoodsVo getGood() {
        return good;
    }

    public void setGood(GoodsVo good) {
        this.good = good;
    }
}
