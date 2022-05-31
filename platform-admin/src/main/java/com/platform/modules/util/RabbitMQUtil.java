/*
 * 项目名称:platform-plus
 * 类名称:RabbitMQUtil.java
 * 包名称:com.platform.modules.util
 *
 * 修改履历:
 *     日期                       修正者        主要内容
 *     2020/9/21 10:21            liuqianru    初版做成
 *
 */
package com.platform.modules.util;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RabbitMQUtil
 *
 * @author liuqianru
 * @date 2020/9/21 10:21
 */
public class RabbitMQUtil {
    //队列名称
//    private static final String QUEUE_NAME = "test_simple_queue";

    public static void getConnection(String queue_name, String jsonData) {
        try {
            Connection connection = null;
            //定义一个连接工厂
            ConnectionFactory factory = new ConnectionFactory();
            //设置服务端地址（域名地址/ip）
            factory.setHost("123.56.178.176");   //内网 172.17.9.22
            //设置服务器端口号
            factory.setPort(5672);
            //设置虚拟主机(相当于数据库中的库)
            factory.setVirtualHost("Yuntu");
            //设置用户名
            factory.setUsername("yuntu");
            //设置密码
            factory.setPassword("yuntuqkj002646");
            connection = factory.newConnection();
            //从连接中获取一个通道
            Channel channel = connection.createChannel();
            //声明队列
            channel.queueDeclare(queue_name, true, false, false, null);
            //发送消息
            channel.basicPublish("", queue_name, null, jsonData.getBytes("utf-8"));
            System.out.println("[RabbitMQ send]：" + jsonData);
            channel.close();
            connection.close();
        } catch (IOException | TimeoutException e) {
            e.printStackTrace();
        }
    }
}
