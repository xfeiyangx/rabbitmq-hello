package com.uestc.rabbitmq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.uestc.rabbitmq.one.Consumer;
import com.uestc.rabbitmq.utils.RabbitMqUtils;
import org.apache.commons.io.filefilter.PrefixFileFilter;

public class Work01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        DeliverCallback deliverCallback = (consumerTag, delivery)->{
            System.out.println("message :" + new String(delivery.getBody()));
        };

        CancelCallback cancelCallback = (consumerTag)->{
            System.out.println("cancel consume message");
        };
        System.out.println("work2 waiting message...");
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback);
    }

}
