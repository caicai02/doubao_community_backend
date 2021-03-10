package com.douyuehan.doubao.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class RabbitMQConfig {

    //交换机
    public static final String EXCHANGE = "delay.exchange";
    //死信队列
    public static final String DELAY_QUEUE = "delay.queue";
    //死信队列与交换机绑定的路由key
    public static final String DELAY_ROUTING_KEY = "delay.key";
    //业务队列
    public static final String TASK_QUEUE_NAME = "task.queue";
    //业务队列与交换机绑定的路由key
    public static final String TASK_ROUTING_KEY = "task.key";

    // 声明交换机
    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    // 声明死信队列
    @Bean
    public Queue delayQueue() {
        Map<String, Object> args = new HashMap<>(2);
        //死信队列消息过期之后要转发的交换机
        args.put("x-dead-letter-exchange", EXCHANGE);
        //消息过期转发的交换机对应的key
        args.put("x-dead-letter-routing-key", TASK_ROUTING_KEY);
        return new Queue(DELAY_QUEUE, true, false, false, args);
    }

    // 声明死信队列绑定关系
    @Bean
    public Binding deadLetterBinding() {
        return BindingBuilder.bind(delayQueue()).to(exchange()).with(DELAY_ROUTING_KEY);
    }

    // 声明业务队列
    @Bean
    public Queue taskQueue() {
        return new Queue(TASK_QUEUE_NAME, true);
    }

    //声明业务队列绑定关系
    @Bean
    public Binding taskBinding() {
        return BindingBuilder.bind(taskQueue()).to(exchange()).with(TASK_ROUTING_KEY);
    }
}
