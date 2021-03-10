package com.douyuehan.doubao.rabbitmq.send;

import com.douyuehan.doubao.model.entity.BmsBillboard;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*RabbitMQ消息确认机制
        消息发送端：
        confirm机制：消息生产者是否成功的将消息发送到交换机。
        return机制：交换机是否成功的将消息发送到队列。
        消息消费端：消息消费者是否成功的从队列获取到了消息。*/
@Component
public class Producer1 {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void sendMsgSimple(){
        BmsBillboard bmsBillboard = new BmsBillboard();
        bmsBillboard.setId(1);
        bmsBillboard.setContent("简单模式");
        bmsBillboard.setShow(true);
        rabbitTemplate.convertAndSend("BmsBillboard","你好简单模式mq");
    }

    public void sendMsgWork(){
        for (int i = 0; i < 10; i++) {
            rabbitTemplate.convertAndSend("quenueWork","你好工作模式mq!");
        }
    }

    public void sendMsgBroadcast() {
        //参数1 交换机 参数2 路由key 参数三 消息
        rabbitTemplate.convertAndSend("fanout-exchange","","这是一条广播消息");
    }

    public void sendDirectMsg() {
        //参数1 交换机 参数2 路由key 参数三 消息
        rabbitTemplate.convertAndSend("direct-exchange","a","这是一条Direct消息");
    }

    public void sendTopicMsg() {
        //参数1 交换机 参数2 路由key 参数三 消息
        rabbitTemplate.convertAndSend("topic-exchange","a.hello","这是一条Tipic消息");
    }
}
