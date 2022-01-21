package com.uestc.rabbitmq.deadQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

public class deadRev {
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(DEAD_QUEUE, false, false, false, null);
        channel.queueBind(DEAD_QUEUE, DEAD_EXCHANGE, "dead");
        System.out.println("dead consumer is waiting...");
        DeliverCallback deliverCallback = (ConsumerTag, message)->{
            System.out.println(new String(message.getBody()));
        };
        channel.basicConsume(DEAD_QUEUE, false, deliverCallback, ConsumerTag->{});
    }
}
