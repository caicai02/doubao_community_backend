package com.douyuehan.doubao.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;
//消息路由到队列监听类
@Slf4j
@Component
public class SendReturnCallback implements RabbitTemplate.ReturnCallback {

    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        log.error("Fail... message:{},从交换机exchange:{},以路由键routingKey:{}," + "未找到匹配队列，replyCode:{},replyText:{}",
                message, exchange, routingKey, replyCode, replyText);
    }

}
