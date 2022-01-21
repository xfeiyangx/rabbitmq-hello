package com.uestc.rabbitmq.six;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

public class DirectExchangeRevConsole {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        String QUEUE_NAME = "console";
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning");
        System.out.println("console is waiting...");
        DeliverCallback deliverCallback = (ConsumerTag, delivery)->{
          String message = new String(delivery.getBody());
          System.out.println("console: "+"routingKey is "+delivery.getEnvelope().getRoutingKey()+"  message is "+ message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, (ConsumerTag)->{});
    }
}
