package com.douyuehan.doubao.rabbitmq;

import com.douyuehan.doubao.utils.ConnectionUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

/**路由模式之-Topic
 * Topic类型的交换机与Direct相比，都是可以根据RoutingKey把消息路由到不同的队列。
 * 只不过Topic类型Exchange可以让队列在绑定Routing key 的时候使用通配符！
 */
public class TopicProducer {
    public static void main(String[] args) throws Exception {
        // 获取RabbitMQ的连接
        Connection connection = ConnectionUtil.getConnection();
        // 从连接中创建通道
        Channel channel = connection.createChannel();
        // 创建队列,如果存在就不创建,不存在就创建
        // 参数1 队列名, 参数2 durable：数据是否持久化 ,参数3 exclusive：是否排外的，记住false就行
        // 参数4 autoDelete：是否自动删除，消费者消费完消息之后是否删除这个队列
        // 参数5 arguments： 其他参数
        channel.queueDeclare("queue05", false, false, false, null);
        channel.queueDeclare("queue06", false, false, false, null);
        //创建交换机，如果存在就不创建。并指定交换机的类型是TOPIC模式
        channel.exchangeDeclare("topic-exchange", BuiltinExchangeType.TOPIC);
        //绑定交换机与队列，第一个参数是队列，第二个参数是交换机，第三个参数是路由key，这里指定路由key是a.*
        //*是通配符，意思只要key满足a开头，.后面是什么都可以
        channel.queueBind("queue05", "topic-exchange", "a.*");
        //绑定交换机与队列，第一个参数是队列，第二个参数是交换机，第三个参数是路由key，这里指定路由key是b.*
        //*是通配符，意思只要key满足b开头，.后面是什么都可以
        channel.queueBind("queue06", "topic-exchange", "b.*");
        //   channel.queueDeclare("queue", false, false, false, null);
        // 消息内容
        String message = "这是一条key为a.hello的消息";
        // 参数1 交换机,此处无
        // 参数2 路由key
        // 参数3 属性
        // 参数4 内容
        channel.basicPublish("topic-exchange", "a.hello", null, message.getBytes());
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
