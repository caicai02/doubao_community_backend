package com.douyuehan.doubao.rabbitmq;

import com.douyuehan.doubao.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class DirectConsumerFour {
    public static void main(String[] args) throws Exception {
        //创建一个新的RabbitMQ连接
        Connection connection = ConnectionUtil.getConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //第一个参数：要从哪个队列获取消息
        channel.basicConsume("queue04",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者04："+new String(body));
            }
        });
    }
}