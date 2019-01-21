package cn.jwb5.SecondKill.redis;

/**
 * Created by jiangwenbin on 2019/1/13.
 */
public class HtmlKey extends BasePrefix {


    public HtmlKey(int expireTime, String prefix) {
        super(expireTime,prefix);
    }

    public static final int time = 60;

    public static final HtmlKey GOODS_LIST_KEY = new HtmlKey(time,"gt");
    public static final HtmlKey GOODS_DETAIL_KEY = new HtmlKey(time,"gd");
}
