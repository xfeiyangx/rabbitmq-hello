package com.uestc.rabbitmq.seven;

import com.rabbitmq.client.Channel;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class TopicSender {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        // 用一个map，存放要routingKey和message
        Map<String,String> routingKeyAndMessage = new HashMap<>();
        routingKeyAndMessage.put("quick.orange.rabbit", "被队列 Q1Q2 接收到");
        routingKeyAndMessage.put("quick.orange.fox", "被队列 Q1 接收到");
        routingKeyAndMessage.put("lazy.brown.fox","被队列 Q2 接收到" );
        for(Map.Entry<String, String> keyAndMsg:routingKeyAndMessage.entrySet()){
            String routingKey = keyAndMsg.getKey();
            String message = keyAndMsg.getValue();
            channel.basicPublish(EXCHANGE_NAME, routingKey,null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("producer have sent msg:" + message);
        }
    }
}
