package com.uestc.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.uestc.rabbitmq.utils.RabbitMqUtils;
import com.uestc.rabbitmq.utils.SleepUtils;

public class Consumer03 {
    public static final String QUEUE_NAME = "hello-ack";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        System.out.println("c1 is short");

        DeliverCallback deliverCallback = (consumerTag,delivery)->{
            String message= new String(delivery.getBody());
            SleepUtils.sleep(1);
            System.out.println("the msg is :"+ message);
            channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
        };
        // set unfair delivery  default:0 fair   1 unfair
        channel.basicQos(1);
        channel.basicConsume(QUEUE_NAME, false, deliverCallback,(consumerTag)->{
            System.out.println(consumerTag+"consumer cancels the callback");
        });
    }
}