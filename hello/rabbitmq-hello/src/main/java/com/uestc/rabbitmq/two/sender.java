package com.uestc.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

import java.util.Scanner;

public class sender {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME, false, false,false, null);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME,null, message.getBytes());
            System.out.println("send "+ message);
        }
    }
}
