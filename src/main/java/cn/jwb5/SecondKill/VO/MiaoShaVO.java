package cn.jwb5.SecondKill.VO;

import cn.jwb5.SecondKill.model.User;

/**
 * Created by jiangwenbin on 2019/1/26.
 */
public class MiaoShaVO {
    private long goodsId;
    private User user;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
