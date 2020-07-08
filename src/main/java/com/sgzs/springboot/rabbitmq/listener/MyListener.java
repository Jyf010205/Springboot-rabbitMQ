package com.sgzs.springboot.rabbitmq.listener;

import com.sgzs.springboot.rabbitmq.config.RabbitMQConfig;
import com.sgzs.springboot.rabbitmq.config.RabbitMQDLXConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/7/7 17:49
 */
@Component
public class MyListener {
    @RabbitListener(queues = RabbitMQConfig.ITEM_QUEUE)
    public void myListen1(String message){
        System.out.println(message);
    }

    @RabbitListener(queues = RabbitMQDLXConfig.DLX_QUEUE)
    public void myListen2(String message){
        System.out.println("死信队列接受消息:" + message);
    }
}
