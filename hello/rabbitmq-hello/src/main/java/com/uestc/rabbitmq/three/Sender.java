package com.uestc.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

import javax.net.ssl.SNIHostName;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Sender {
    public static final String QUEUE_NAME = "hello-ack";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("", QUEUE_NAME, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes(StandardCharsets.UTF_8));
            System.out.println("sender "+ message);
        }
    }
}
