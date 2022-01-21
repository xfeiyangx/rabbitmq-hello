package com.uestc.rabbitmq.six;

import com.rabbitmq.client.Channel;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

import java.util.HashMap;
import java.util.Map;

public class DirectExchangeSender {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");

        Map<String, String> bindingKeyMap= new HashMap<>();
        bindingKeyMap.put("info", "information");
        bindingKeyMap.put("warning", "warning");
        bindingKeyMap.put("error", "error");
        bindingKeyMap.put("debug", "debug");

        for(Map.Entry<String, String> bindingKeyEntry: bindingKeyMap.entrySet()){
            String bindingKey = bindingKeyEntry.getKey();
            String message = bindingKeyEntry.getValue();
            channel.basicPublish(EXCHANGE_NAME, bindingKey,null, message.getBytes());
            System.out.println("sender: "+ message);
        }

    }
}
