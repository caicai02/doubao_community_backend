package com.douyuehan.doubao.rabbitmq;

import com.douyuehan.doubao.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**Routing之订阅模型-Direct(直连)
 * 举个例子：消息生产者发送消息时给了交换机一个红桃A，消息生产者对交换机说：”这条消息只能给有红桃A的队列“。
 * 交换机发现队列一手里是黑桃K，队列二手里是红桃A，所以它将这条消息给了队列二。
 */
public class DirectProducer {
    public static void main(String[] args) throws Exception {
        // 获取RabbitMQ的连接
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 创建队列,如果存在就不创建,不存在就创建
        // 参数1 队列名, 参数2 durable：数据是否持久化 ,参数3 exclusive：是否排外的，记住false就行
        // 参数4 autoDelete：是否自动删除，消费者消费完消息之后是否删除这个队列
        // 参数5 arguments： 其他参数
        channel.queueDeclare("queue03", false, false, false, null);
        channel.queueDeclare("queue04", false, false, false, null);
        //创建交换机，如果存在就不创建。并指定交换机的类型是DIRECT模式
        channel.exchangeDeclare("direct-exchange", BuiltinExchangeType.DIRECT);
        //绑定交换机与队列，第一个参数是队列，第二个参数是交换机，第三个参数是路由key，这里指定路由key是a
        channel.queueBind("queue03", "direct-exchange", "a");
        //绑定交换机与队列，第一个参数是队列，第二个参数是交换机，第三个参数是路由key，这里指定路由key是b
        channel.queueBind("queue04", "direct-exchange", "b");
        //消息
        String message = "这是一条key为a的消息";
        // 参数1 交换机
        // 参数2 路由key
        // 参数3 属性
        // 参数4 内容
        channel.basicPublish("direct-exchange", "a", null, message.getBytes());
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}

