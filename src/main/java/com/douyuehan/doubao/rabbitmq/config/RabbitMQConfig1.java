package com.douyuehan.doubao.rabbitmq.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
//重新注入RabbitTemplate,并设置两个监听类
@Configuration
public class RabbitMQConfig1 {

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setConfirmCallback( new SendConfirmCallback());
        rabbitTemplate.setReturnCallback(new SendReturnCallback());
        return rabbitTemplate;
    }

}
