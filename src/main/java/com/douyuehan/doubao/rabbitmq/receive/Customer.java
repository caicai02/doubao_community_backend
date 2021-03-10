package com.douyuehan.doubao.rabbitmq.receive;

import com.douyuehan.doubao.rabbitmq.config.RabbitMQConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
/*
@Component*/
/*public class Consumer {

    @Autowired
    //private OrderService orderService;

    //监听业务队列
    @RabbitListener(queues = RabbitMQConfig.TASK_QUEUE_NAME)
    public void receiveTask(Message message){
        String orderId = new String(message.getBody());
        //log.info("过期的任务Id：{}", orderId);
        //Order order = orderService.getById(orderId);
        //如果订单支付状态仍为未支付
        //if(order.getPayState()==0){
            //设置该订单状态为已关闭
            //order.setPayState(2);
            //orderService.updateById(order);
        }
}*/
