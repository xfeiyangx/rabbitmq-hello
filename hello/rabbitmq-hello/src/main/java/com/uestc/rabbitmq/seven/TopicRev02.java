package com.uestc.rabbitmq.seven;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

public class TopicRev02 {
    public static final String EXCHANGE_NAME = "topic_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        String QUEUE_NAME = "Q2";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "*.*.rabbit");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "lazy.#");
        DeliverCallback deliverCallback = (ConsumerTag, delivery) ->{
            String message = new String(delivery.getBody());
            System.out.println(QUEUE_NAME + ":"+ delivery.getEnvelope().getRoutingKey()+ ": "+ message);
        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, ConsumerTag ->{});
    }
}
