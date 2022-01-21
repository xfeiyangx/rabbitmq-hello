package com.uestc.rabbitmq.four;


import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

public class ConfirmPublish {
    public static void main(String[] args) throws Exception {
//        ConfirmPublish.SinglePublish(); //single cost time 192 ms
//        ConfirmPublish.BatchConfirm(); //batch cost time 28 ms
        ConfirmPublish.AsyncConfirm(); //Async cost time 27 ms

    }

    public static void SinglePublish() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        String QUEUE_NAME = UUID.randomUUID().toString();
        channel.confirmSelect();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        long begin = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            boolean flag = channel.waitForConfirms();
            if (flag) {
                System.out.println("msg have sent successfully.");
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("single cost time " + (end - begin) + " ms");
    }

    public static void BatchConfirm() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.confirmSelect();
        String QUEUE_NAME = UUID.randomUUID().toString();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        final int BatchSize = 100;
        long begin = System.currentTimeMillis();
        for (int i = 1; i <= 1000; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            if (i % BatchSize == 0) {
                boolean flag = channel.waitForConfirms();
                if (flag) {
                    System.out.println("msg have sent successfully.");
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("batch cost time " + (end - begin) + " ms");
    }

    public static void AsyncConfirm() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.confirmSelect();
        String QUEUE_NAME = UUID.randomUUID().toString();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        ConcurrentSkipListMap<Long, String> outConfirm = new ConcurrentSkipListMap<>();

        ConfirmCallback ackCallback = (SeqNumber, multiple) -> {
            System.out.println("this is ack " + SeqNumber + " msg");
            outConfirm.remove(SeqNumber); // after confirming , then delete
        };
        ConfirmCallback nackCallback = (SeqNumber, multiple) -> {
            System.out.println("this is nack " + SeqNumber + " msg");
        };

        channel.addConfirmListener(ackCallback, nackCallback);

        long begin = System.currentTimeMillis();

        for (int i = 0; i < 1000; i++) {
            String message = i + "";
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            outConfirm.put(channel.getNextPublishSeqNo(), message); // record all message in a hashmap
        }
        long end = System.currentTimeMillis();
        System.out.println("Async cost time " + (end - begin) + " ms");
    }
}
