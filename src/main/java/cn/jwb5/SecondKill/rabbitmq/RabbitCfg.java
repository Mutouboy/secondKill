package cn.jwb5.SecondKill.rabbitmq;

import com.alibaba.druid.sql.visitor.functions.Bin;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiangwenbin on 2019/1/21.
 */
@Configuration
public class RabbitCfg {


    public static final String QUEUE = "queue";

    public static final String TOPIC_QUEUE1 = "topic-queue1";
    public static final String TOPIC_QUEUE2 = "topic-queue2";
    public static final String EXCHANGER = "exchanger";
    public static final String KEY1 = "key1";
    public static final String KEY2 = "#";

    public static final String FANOUT_EXCHANGER = "fanout-exchanger";

    public static final String HEADERS_QUEUE = "headers-queue";
    public static final String HEADERS_EXCHANGER = "headers-exchanger";


    @Bean
    public Queue queue(){
        return new Queue(QUEUE,true);
    }
    @Bean
    public Queue queue1(){
        return new Queue(TOPIC_QUEUE1,true);
    }
    @Bean
    public Queue queue2(){
        return new Queue(TOPIC_QUEUE2,true);
    }


    @Bean
    public TopicExchange exchange(){
        return new TopicExchange(EXCHANGER);
    }

    @Bean
    public Binding bindTopic1(){
        return BindingBuilder.bind(queue1()).to(exchange()).with(KEY1);
    }

    @Bean
    public Binding bindTopic2(){
        return BindingBuilder.bind(queue2()).to(exchange()).with(KEY2);
    }


    @Bean
    public FanoutExchange fanoutExchange(){
        return new FanoutExchange(FANOUT_EXCHANGER);
    }

    @Bean
    public Binding bindFanout1(){
        return BindingBuilder.bind(queue1()).to(fanoutExchange());
    }

    @Bean
    public Binding bindFanout2(){
        return BindingBuilder.bind(queue2()).to(fanoutExchange());
    }


    @Bean
    public Queue headersQueue(){
        return new Queue(HEADERS_QUEUE);
    }

    @Bean
    public HeadersExchange headersExchange(){
        return new HeadersExchange(HEADERS_EXCHANGER);
    }

    @Bean
    public Binding bindHeaders(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("k1","v1");
        map.put("k2","v2");
        return BindingBuilder.bind(headersQueue()).to(headersExchange()).whereAll(map).match();
    }



    public static final String MIAOSHAQUEUE = "miaoshaqueue";

    @Bean
    public Queue getMiaoshaQueue(){
        return new Queue(MIAOSHAQUEUE);
    }

}
