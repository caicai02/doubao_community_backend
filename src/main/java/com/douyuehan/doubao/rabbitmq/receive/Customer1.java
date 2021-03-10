package com.douyuehan.doubao.rabbitmq.receive;

import com.douyuehan.doubao.model.entity.BmsBillboard;
import com.rabbitmq.client.Channel;
import org.junit.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Customer1 {

    @RabbitHandler
    public void process(String content, Channel channel, Message message) {
        try {
            // 业务处理成功后调用，消息会被确认消费
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            // 业务处理失败后调用
            //channel.basicNack(message.getMessageProperties().getDeliveryTag(),false, true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //监听的队列
    @RabbitListener(queues = "queueSimple")
    public void receiveSimple(BmsBillboard bmsBillboard){
        System.out.println("消息：" + bmsBillboard);
    }

    @RabbitListener(queues = "quenueWork")
    public void customerWorkOne(String message){
        System.out.println("消费者一：" + message);
    }
    @RabbitListener(queues = "quenueWork")
    public void customerWorkTwo(String message){
        System.out.println("消费者二：" + message);
    }

    @RabbitListener(queues = "queueBroadcast01")
    public void customerBroaccastOne(String message){
        System.out.println("消费者一：" + message);
    }
    @RabbitListener(queues = "queueBroadcast02")
    public void customerBroadcastTwo(String message){
        System.out.println("消费者二：" + message);
    }

    //监听的队列 queueDirect03
    @RabbitListener(queues = "queueDirect03")
    public void customerDirectOne(String message){
        System.out.println("消费者一：" + message);
    }
    //监听的队列 queueDirect04
    @RabbitListener(queues = "queueDirect04")
    public void customerDirectTwo(String message){
        System.out.println("消费者二：" + message);
    }

    //监听的队列 queueTipic05
    @RabbitListener(queues = "queueTipic05")
    public void customerTopicOne(String message){
        System.out.println("消费者一：" + message);
    }
    //监听的队列 queueTipic06
    @RabbitListener(queues = "queueTipic06")
    public void customerTopicTwo(String message){
        System.out.println("消费者二：" + message);
    }
}
