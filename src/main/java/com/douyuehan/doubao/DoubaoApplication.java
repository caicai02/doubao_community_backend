package com.douyuehan.doubao;

import com.douyuehan.doubao.rabbitmq.service.ReceiveService;
import com.douyuehan.doubao.rabbitmq.service.SendService;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@MapperScan("com.douyuehan.doubao.mapper")
@SpringBootApplication
public class DoubaoApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(DoubaoApplication.class, args);
        SendService sendService = (SendService) run.getBean("sendService");
        sendService.sendDirectMessage("");
        sendService.sendFanoutMessage("");
        sendService.sendTopicMessage("aa.bb");
        run.getBean("receiveService");
    }
}
