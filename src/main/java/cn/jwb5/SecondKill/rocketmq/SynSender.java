package cn.jwb5.SecondKill.rocketmq;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenbin on 2019/1/26.
 */
@Service
public class SynSender {

    public void send(String msg) throws Exception{
        DefaultMQProducer producer = new DefaultMQProducer(RocketConf.SYNCSEND);
        producer.setNamesrvAddr(RocketConf.ADDRESS);
        producer.start();
        for (int i = 0;i<10;i++){
            Message message = new Message("test","tag1",(msg+i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n",sendResult);
        }

        for (int i = 0;i<10;i++){
            Message message = new Message("test","tag2",(msg+i+10).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(message);
            System.out.printf("%s%n",sendResult);
        }
        producer.shutdown();
    }

}
