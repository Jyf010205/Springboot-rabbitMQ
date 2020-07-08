package com.sgzs.springboot.rabbitmq.controller;

import com.sgzs.springboot.rabbitmq.config.RabbitMQConfig;
import com.sgzs.springboot.rabbitmq.config.RabbitMQDLXConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author: jianyufeng
 * @description:
 * @date: 2020/7/7 17:44
 */
@RestController
public class SendMsgController {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 测试
     */
    @GetMapping("/sendmsg")
    public String sendMsg(@RequestParam String msg, @RequestParam String key){
        /**
         * 发送消息
         * 参数一：交换机名称
         * 参数二：路由key
         * 参数三：发送的消息
         */
        rabbitTemplate.convertAndSend(RabbitMQConfig.ITEM_TOPIC_EXCHANGE ,key ,msg);
        //返回消息
        return "发送消息成功！";
    }

    @GetMapping("/testTTL")
    public String testTTL(@RequestParam String msg){
        rabbitTemplate.convertAndSend(RabbitMQConfig.TTL_QUEUE,msg);
        //返回消息
        return "发送消息成功！";
    }

    @GetMapping("/testTTLDLX")
    public String testTTLDLX(){
        rabbitTemplate.convertAndSend(RabbitMQDLXConfig.NORMAL_EXCHANGE,RabbitMQDLXConfig.DLX_TTL_KEY,"测试TTL队列");
        return "发送消息成功！";
    }

    @GetMapping("/testMAXDLX")
    public String testMAXDLX(){
        rabbitTemplate.convertAndSend(RabbitMQDLXConfig.NORMAL_EXCHANGE,RabbitMQDLXConfig.DLX_MAX_KEY,"测试MAX队列");
        return "发送消息成功！";
    }
}
