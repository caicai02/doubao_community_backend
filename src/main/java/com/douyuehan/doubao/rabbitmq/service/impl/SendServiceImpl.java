package com.douyuehan.doubao.rabbitmq.service.impl;

import com.douyuehan.doubao.rabbitmq.service.SendService;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.douyuehan.doubao.rabbitmq.config.RabbitMQConfig.*;

@Service("sendService")
public class SendServiceImpl implements SendService {
    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public void sendDirectMessage(String message) {
        //参数1 交换机 参数2 路由key 参数三 消息
        amqpTemplate.convertAndSend(DIRECT_EXCHANGE,DIRECT_ROUTING_KEY,"这是一条Direct消息+1");
    }

    @Override
    public void sendFanoutMessage(String message) {
        //参数1 交换机 参数2 路由key 参数三 消息
        amqpTemplate.convertAndSend(FANOUT_EXCHANGE,"","这是一个广播信息");
    }

    @Override
    public void sendTopicMessage(String message) {
        //参数1 交换机 参数2 路由key 参数三 消息
        amqpTemplate.convertAndSend(TOPIC_EXCHANGE,TOPIC_ROUTING_KEY,"这是一个Topic信息");
    }
}
