package com.douyuehan.doubao.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//可以不做绑定,但必须确认交换机是存在的
@Configuration
public class RabbitMQConfig {

    //Direct交换机
    public static final String DIRECT_EXCHANGE = "direct.exchange";
    //广播交换机
    public static final String FANOUT_EXCHANGE = "fanout.exchange";
    //topic交换机
    public static final String TOPIC_EXCHANGE = "topic.exchange";
    //Direct队列
    public static final String DIRECT_QUEUE = "direct.queue";
    //队列与Direct交换机绑定的路由key
    public static final String DIRECT_ROUTING_KEY = "direct.key";
    //队列与Topic交换机绑定的路由key
    public static final String TOPIC_ROUTING_KEY = "topic.key";

    // 声明Direct交换机
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(DIRECT_EXCHANGE);
    }
    // 声明Fanout交换机
    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE);
    }
    // 声明Topic交换机
    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(TOPIC_EXCHANGE);
    }

    // 声明队列
    @Bean
    public Queue directQueue() {
        return new Queue(DIRECT_QUEUE);
    }

    /**绑定交换机和队列
     * @param directQueue 队列，参数名必须和某个bean方法名同
     * @param directExchange 交换机，参数名必须和某个bean方法名同
     * @return
     */
    @Bean
    public Binding directBinding(Queue directQueue,DirectExchange directExchange) {
        //完成绑定：参数1 要绑定的队列
        //         参数2 要绑定的交换机
        //         参数3 绑定时的RoutingKey
        return BindingBuilder.bind(directQueue).to(directExchange).with(DIRECT_ROUTING_KEY);
    }

}
