package cn.jwb5.SecondKill.redis;

/**
 * Created by jiangwenbin on 2019/1/18.
 */
public class GoodsKey extends BasePrefix {

    private GoodsKey(int expireTime, String prefix) {
        super(expireTime, prefix);
    }

    private GoodsKey(String prefix) {
        super(prefix);
    }

    private static final int GoodsKeyExpireTime = 3600;

    public static final GoodsKey byId = new GoodsKey(GoodsKeyExpireTime,"gid");

    public static final GoodsKey miaoshaById = new GoodsKey(0,"msgid");
}
