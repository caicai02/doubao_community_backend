package com.douyuehan.doubao.rabbitmq;

import com.douyuehan.doubao.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**广播模式
 * 这里引入了交换机(Exchange)的概念，交换机绑定所有的队列。
 * 也就是说消息生产者会先把消息发送给交换机，然后交换机把消息发送到与它绑定的所有队列里面，消费者从它所绑定的队列里面获取消息。
 */
public class BroadcastProducer {
    public static void main(String[] args) throws Exception {
        // 获取RabbitMQ的连接
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 创建队列,如果存在就不创建,不存在就创建
        // 参数1 队列名, 参数2 durable：数据是否持久化 ,参数3 exclusive：是否排外的，记住false就行
        // 参数4 autoDelete：是否自动删除，消费者消费完消息之后是否删除这个队列
        // 参数5 arguments： 其他参数
        channel.queueDeclare("queue01", false, false, false, null);
        channel.queueDeclare("queue02", false, false, false, null);
        //创建交换机，如果存在就不创建。并指定交换机的类型是FANOUT即广播模式
        channel.exchangeDeclare("fanout-exchange", BuiltinExchangeType.FANOUT);
        //绑定交换机与队列，第一个参数是队列，第二个参数是交换机，第三个参数是路由key,这里不指定key
        channel.queueBind("queue01", "fanout-exchange", "");
        channel.queueBind("queue02", "fanout-exchange", "");
        // 消息内容
        String message = "这是一条广播消息";
        // 参数1 交换机
        // 参数2 发送到哪个队列，因为指定了交换机，所以这里队列名为空
        // 参数3 属性
        // 参数4 内容
        channel.basicPublish("fanout-exchange", "", null, message.getBytes());
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
