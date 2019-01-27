package cn.jwb5.SecondKill.rocketmq;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListener;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;
import org.springframework.stereotype.Service;
import sun.jvm.hotspot.runtime.Thread;

import java.util.List;

/**
 * Created by jiangwenbin on 2019/1/26.
 */
@Service
public class Consumer {

    public void consumer() throws Exception {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(RocketConf.PUSHCONSUMER);
        consumer.setNamesrvAddr(RocketConf.ADDRESS);
        consumer.subscribe("test","tag1");
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                System.out.printf("%s receive New msg : %s %n", java.lang.Thread.currentThread().getName(),msgs);
                System.out.println(new String(msgs.get(0).getBody()));
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        consumer.start();
        System.out.printf("Consumer started.%n");
    }
}
