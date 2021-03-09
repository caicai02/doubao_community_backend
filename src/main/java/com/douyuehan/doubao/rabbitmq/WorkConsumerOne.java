package com.douyuehan.doubao.rabbitmq;

import com.douyuehan.doubao.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class WorkConsumerOne {
    public static void main(String[] args) throws Exception {
        //创建一个RabbitMq的连接
        Connection connection = ConnectionUtil.getConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        channel.basicConsume("queue",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者01："+new String(body));
            }
        });
    }
}
