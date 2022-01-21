package com.uestc.rabbitmq.six;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

public class DirectExchangeRevDisk {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        String QUEUE_NAME = "disk";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");

        System.out.println("disk error is waiting...");

        DeliverCallback deliverCallback = (ConsumerTag, delivery)->{
          String message = new String(delivery.getBody());
            System.out.println("disk error get message: "+ message);
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, (consumerTag)->{});
    }
}
