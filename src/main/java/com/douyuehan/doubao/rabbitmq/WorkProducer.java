package com.douyuehan.doubao.rabbitmq;

import com.douyuehan.doubao.utils.ConnectionUtil;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**工作模式：先到先得
 * 说白了就是一个生产者发送消息，多个消费者接受消息。
 * 只要其中的一个消费者抢先接收到了消息，其他的就接收不到了。一对多的关系。
 */
public class WorkProducer {
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
        String message = "你好啊，工作模式mq";
        // 参数1 交换机,此处无
        // 参数2 发送到哪个队列
        // 参数3 属性
        // 参数4 内容
        for (int i = 0; i < 10; i++) {
            channel.basicPublish("", "queue", null, (message+i).getBytes());
        }
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
