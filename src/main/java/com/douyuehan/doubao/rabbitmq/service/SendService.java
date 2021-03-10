package com.douyuehan.doubao.rabbitmq.service;

public interface SendService {
    void sendDirectMessage(String message);

    void sendFanoutMessage(String message);
    void sendTopicMessage(String message);
}
