package com.douyuehan.doubao.rabbitmq;

import com.douyuehan.doubao.utils.ConnectionUtil;
import com.rabbitmq.client.*;

import java.io.IOException;

public class SimpleConsumer {
    public static void main(String[] args) throws Exception {
        //获取RabbitMq的连接
        Connection connection = ConnectionUtil.getConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //第一个参数：要从哪个队列获取消息
        channel.basicConsume("queue",true,new DefaultConsumer(channel){
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("简单模式获取消息："+new String(body));
            }
        });
    }
}
