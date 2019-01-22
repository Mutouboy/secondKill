package cn.jwb5.SecondKill.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenbin on 2019/1/21.
 */

@Service
public class MQSender {

    private static final Logger log = LoggerFactory.getLogger(MQSender.class);

    @Autowired
    AmqpTemplate amqpTemplate;


    public void sendMsg(String msg){
        log.info("send "+msg);
        amqpTemplate.convertAndSend(RabbitCfg.QUEUE,msg);
    }

    public void sendMsg2Top(String msg){
        log.info("send "+msg);
        amqpTemplate.convertAndSend(RabbitCfg.EXCHANGER,"key1",msg+"1");
        amqpTemplate.convertAndSend(RabbitCfg.EXCHANGER,"key2",msg+"2");

    }

    public void sendMsg2Fanout(String msg){
        log.info("send "+msg);
        amqpTemplate.convertAndSend(RabbitCfg.FANOUT_EXCHANGER,"",msg);

    }

    public void sendMsg2Header(String msg){
        log.info("send "+msg);
        MessageProperties mp = new MessageProperties();
        mp.setHeader("k1","v1");
        mp.setHeader("k2","v2");
        Message obj = new Message(msg.getBytes(),mp);
        amqpTemplate.convertAndSend(RabbitCfg.HEADERS_EXCHANGER,"",obj);

    }





}
