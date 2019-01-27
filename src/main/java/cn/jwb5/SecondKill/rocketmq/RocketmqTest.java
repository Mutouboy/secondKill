package cn.jwb5.SecondKill.rocketmq;

/**
 * Created by jiangwenbin on 2019/1/26.
 */
public class RocketmqTest {

    public static void main(String[] args) throws Exception {
        SynSender sender = new SynSender();
        Consumer consumer = new Consumer();

        sender.send("rocket teset");
        consumer.consumer();
    }
}
