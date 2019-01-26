package cn.jwb5.SecondKill.rabbitmq;

import cn.jwb5.SecondKill.VO.MiaoShaVO;
import cn.jwb5.SecondKill.redis.RedisService;
import cn.jwb5.SecondKill.service.MiaoshaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenbin on 2019/1/21.
 */
@Service
public class MQreceiver {

    @Autowired
    MiaoshaService miaoshaService;

    @Autowired
    RedisService redisService;

    private static Logger log = LoggerFactory.getLogger(MQreceiver.class);

    @RabbitListener(queues = RabbitCfg.QUEUE)
    public void receive(String msg){
        log.info("receive "+msg);
    }

    @RabbitListener(queues = RabbitCfg.TOPIC_QUEUE1)
    public void receive1(String msg){
        log.info("receive topic1"+msg);
    }

    @RabbitListener(queues = RabbitCfg.TOPIC_QUEUE2)
    public void receive2(String msg){
        log.info("receive topic2"+msg);
    }

    @RabbitListener(queues = RabbitCfg.HEADERS_QUEUE)
    public void receive3(byte[] msg){
        log.info("receive headers"+new String(msg));
    }

    @RabbitListener(queues = RabbitCfg.MIAOSHAQUEUE)
    public void receive4(String msg){
        MiaoShaVO miaoShaVO = redisService.string2Bean(msg, MiaoShaVO.class);
        miaoshaService.miaosha(miaoShaVO);
    }

}
