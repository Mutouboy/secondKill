package cn.jwb5.SecondKill.redis;

/**
 * Created by jiangwenbin on 2019/1/8.
 */
public interface KeyPrefix {
    int expireTime();
    String getPrefix();
}
