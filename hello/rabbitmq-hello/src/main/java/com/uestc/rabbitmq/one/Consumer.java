package com.uestc.rabbitmq.one;

import com.rabbitmq.client.*;

public class Consumer {
    public static final String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setUsername("guest");
        factory.setPassword("guest");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        
        //声明消费的回调函数和未消费成功的回调函数
        DeliverCallback deliverCallback = (consumerTag, delivery)->{
            System.out.println(new String(delivery.getBody()));
        };
        CancelCallback cancelCallback = (ConsumerTag)->{
            System.out.println("not consume the message!");
        };
        
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, cancelCallback );
    }
    
}
