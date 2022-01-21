package com.uestc.rabbitmq.deadQueue;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

public class Producer {
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        AMQP.BasicProperties properties = new AMQP.BasicProperties().builder().expiration("10000").build();
        for (int i = 1; i < 11; i++) {
            String message = "information" + i;
            channel.basicPublish(NORMAL_EXCHANGE, "normal", properties, message.getBytes());
        }
    }
}
