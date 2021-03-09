package com.douyuehan.doubao.rabbitmq;

import com.douyuehan.doubao.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**简单模式
 * 说白了就是一个生产者发送消息，一个消费者接受消息，一对一的关系。
 */
public class SimpleProducer {
    public static void main(String[] args) throws Exception {
        // 获取RabbitMQ的连接
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 创建队列,如果存在就不创建,不存在就创建
        // 参数1 队列名, 参数2 durable：数据是否持久化 ,参数3 exclusive：是否排外的，记住false就行
        // 参数4 autoDelete：是否自动删除，消费者消费完消息之后是否删除这个队列
        // 参数5 arguments： 其他参数
        channel.queueDeclare("queue", false, false, false, null);
        // 写到队列中的消息内容
        String message = "你好啊，mq!";
        // 参数1 交换机,此处没有
        // 参数2 发送到哪个队列
        // 参数3 属性
        // 参数4 内容
        channel.basicPublish("", "queue", null, message.getBytes());
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
