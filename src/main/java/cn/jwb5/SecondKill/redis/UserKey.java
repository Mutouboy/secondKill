package cn.jwb5.SecondKill.redis;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
public class UserKey extends BasePrefix{

    private UserKey(String prefix) {
        super(prefix);
    }

    private UserKey(int expireTime, String prefix) {
        super(expireTime, prefix);
    }

    private static final int TOKEN_EXPIRETIME = 3600*24;//24h

    public static UserKey getById = new UserKey("id");
    public static UserKey getByName = new UserKey("name");
    public static UserKey getByToken = new UserKey(TOKEN_EXPIRETIME,"tk");



}
