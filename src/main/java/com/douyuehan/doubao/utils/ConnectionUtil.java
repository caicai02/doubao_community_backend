package com.douyuehan.doubao.utils;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
public class ConnectionUtil {
    public static Connection getConnection() throws Exception {
        //定义连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //ip地址
        factory.setHost("192.168.126.130");
        //端口
        factory.setPort(5672);
        //虚拟主机
        factory.setVirtualHost("myhost");
        //账户
        factory.setUsername("root");
        //密码
        factory.setPassword("root");
        Connection connection = factory.newConnection();
        return connection;
    }
}
