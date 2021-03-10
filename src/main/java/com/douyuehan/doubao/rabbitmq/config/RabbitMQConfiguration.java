package com.douyuehan.doubao.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfiguration {

    //创建队列
    @Bean
    public Queue queue1(){
        Queue queue1 = new Queue("queue1");
        return queue1;
    }
    @Bean
    public Queue queue2(){
        Queue queue2 = new Queue("queue2");
        //设置队列属性
        return queue2;
    }

    //创建广播模式交换机
    @Bean
    public FanoutExchange ex1(){
        return new FanoutExchange("ex1");
    }

    //创建路由模式-direct交换机
    @Bean
    public DirectExchange ex2(){
        return new DirectExchange("ex2");
    }

    //绑定队列
    @Bean
    public Binding bindingQueue1(Queue queue1, DirectExchange ex2){
        return BindingBuilder.bind(queue1).to(ex2).with("a1");
    }
    @Bean
    public Binding bindingQueue2(Queue queue2, DirectExchange ex2){
        return BindingBuilder.bind(queue2).to(ex2).with("a2");
    }
}

