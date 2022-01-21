package com.uestc.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

public class ExchangeRev2 {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        String QUEUE_NAME = channel.queueDeclare().getQueue();
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME,"");
        System.out.println("waiting receive messages...");

        DeliverCallback deliverCallback = (ConsumerTag, delivery)->{
            String message = new String(delivery.getBody());
            System.out.println("rev msg: "+ message);
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, (ConsumerTag)->{});
    }
}
