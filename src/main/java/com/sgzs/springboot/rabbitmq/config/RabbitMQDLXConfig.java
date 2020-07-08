package com.sgzs.springboot.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: jianyufeng
 * @description: 死信队列
 * @date: 2020/7/7 18:48
 */
@Configuration
public class RabbitMQDLXConfig {
    public static final String DLX_QUEUE = "my_dlx_queue";

    public static final String TTL_DLX_QUEUE = "my_ttl_dlx_queue";

    public static final String MAX_DLX_QUEUE = "my_max_dlx_queue";

    public static final String DLX_EXCHANGE = "my_dlx_exchange";

    public static final String NORMAL_EXCHANGE = "my_normal_exchange";

    public static final String DLX_TTL_KEY = "my_dlx_ttl_key";

    public static final String DLX_MAX_KEY = "my_dlx_max_key";


    //声明死信队列
    @Bean
    public Queue dlxQueue(){
        return QueueBuilder.durable(DLX_QUEUE).build();
    }

    //声明死信交换机
    @Bean
    public Exchange directExchange(){
        return ExchangeBuilder.directExchange(DLX_EXCHANGE).durable(true).build();
    }

    //绑定队列和交换机
    @Bean
    public Binding dlxTTLQueueExchange(){
        return BindingBuilder.bind(dlxQueue()).to(directExchange()).with(DLX_TTL_KEY).noargs();
    }
    @Bean
    public Binding dlxMAXQueueExchange(){
        return BindingBuilder.bind(dlxQueue()).to(directExchange()).with(DLX_MAX_KEY).noargs();
    }

    //声明超时队列
    @Bean
    public Queue ttlDlxQueue(){
        return QueueBuilder
                .durable(TTL_DLX_QUEUE)
                .withArgument("x-message-ttl",6000)
                .withArgument("x-dead-letter-exchange",DLX_EXCHANGE)
                .build();
    }

    //声明限制长度队列
    @Bean
    public Queue maxDlxQueue(){
        return QueueBuilder
                .durable(MAX_DLX_QUEUE)
                .withArgument("x-max-length",2)
                .withArgument("x-dead-letter-exchange",DLX_EXCHANGE)
                .build();
    }

    //声明定向交换机
    @Bean
    public Exchange normalExchange(){
        return ExchangeBuilder.directExchange(NORMAL_EXCHANGE).durable(true).build();
    }

    @Bean
    public Binding TTLQueueExchange(){
        return BindingBuilder.bind(ttlDlxQueue()).to(normalExchange()).with(DLX_TTL_KEY).noargs();
    }
    @Bean
    public Binding MAXQueueExchange(){
        return BindingBuilder.bind(maxDlxQueue()).to(normalExchange()).with(DLX_MAX_KEY).noargs();
    }

}
