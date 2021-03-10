package com.douyuehan.doubao.rabbitmq.send;

import com.douyuehan.doubao.rabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Producer {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    //orderId 是订单id interval是自定义过期时间 单位：秒
    public void orderDelay(String orderId,Long interval) {
        MessageProperties messageProperties = new MessageProperties();
        //设置消息过期时间
        messageProperties.setExpiration(String.valueOf(interval));
        Message message = new Message(orderId.getBytes(), messageProperties);
        //生产者将消息发给死信队列，并设置消息过期时间
        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, null, message);
    }
}
