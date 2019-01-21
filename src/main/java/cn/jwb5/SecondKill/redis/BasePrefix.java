package cn.jwb5.SecondKill.redis;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
public abstract class BasePrefix implements KeyPrefix {

    private int expireTime;
    private String prefix;

    public BasePrefix(int expireTime, String prefix) {
        this.expireTime = expireTime;
        this.prefix = prefix;
    }

    public BasePrefix(String prefix) {
        this(0,prefix);
    }

    @Override
    public int expireTime() {
        return expireTime;
    }

    @Override
    public String getPrefix() {
        String className = getClass().getSimpleName();
        return className+":"+prefix;
    }
}
