package com.douyuehan.doubao.rabbitmq.receive;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import static com.douyuehan.doubao.rabbitmq.config.RabbitMQConfig.*;

@Service("receiveService")
public class ReceiveServiceImpl implements ReceiveService {

    @Resource
    private AmqpTemplate amqpTemplate;

    /**Routing之订阅模型-Direct(直连)
     * 消息生产者发送消息时给了交换机一个红桃A，消息生产者对交换机说：”这条消息只能给有红桃A的队列“。
     * 交换机发现队列一手里是黑桃K，队列二手里是红桃A，所以它将这条消息给了队列二。
     * @param message
     */
    //自动持续监听某个队列
    @RabbitListener(queues = {DIRECT_QUEUE})
    public void directReceive(String message) {
        System.out.println("directReceive: "+message);
    }

    /**广播模式
     * 这里引入了交换机(Exchange)的概念，交换机绑定所有的队列。
     * 也就是说消息生产者会先把消息发送给交换机，然后交换机把消息发送到与它绑定的所有队列里面，消费者从它所绑定的队列里面获取消息。
     * @param message
     */
    //随机队列和交换机绑定
    @RabbitListener(bindings={@QueueBinding(value=@Queue(),exchange = @Exchange(name=FANOUT_EXCHANGE,type="fanout"))})
    public void fanoutReceive01(String message) {
        System.out.println("fanoutReceive01: "+message);
    }
    //随机队列和交换机绑定
    @RabbitListener(bindings={@QueueBinding(value=@Queue(),exchange = @Exchange(name=FANOUT_EXCHANGE,type="fanout"))})
    public void fanoutReceive02(String message) {
        System.out.println("fanoutReceive02: "+message);
    }

    /**Routing 之订阅模型-Topic
     * 消息生产者发送消息时给了交换机一个暗号：hello.mq，消息生产者对交换机说：”这条消息只能给暗号以hello开头的队列“。
     * 交换机发现它与队列一的暗号是hello.java，与队列二的暗号是news.today，所以它将这条消息给了队列一。
     * @param message
     */
    //队列topic01和交换机topic绑定
    @RabbitListener(bindings={@QueueBinding(value=@Queue("topic01"),key = {"topic"},exchange = @Exchange(name=TOPIC_EXCHANGE,type="topic"))})
    public void topicReceive01(String message) {
        System.out.println("topicReceive01--topic--: "+message);
    }
    //队列topic02和交换机topic绑定
    @RabbitListener(bindings={@QueueBinding(value=@Queue("topic02"),key = {"topic.*"},exchange = @Exchange(name=TOPIC_EXCHANGE,type="topic"))})
    public void topicReceive02(String message) {
        System.out.println("topicReceive02--topic.*--: "+message);
    }
    //队列topic03和交换机topic绑定
    @RabbitListener(bindings={@QueueBinding(value=@Queue("topic03"),key = {"topic.#"},exchange = @Exchange(name=TOPIC_EXCHANGE,type="topic"))})
    public void topicReceive03(String message) {
        System.out.println("topicReceive03--topic.#--: "+message);
    }
}
