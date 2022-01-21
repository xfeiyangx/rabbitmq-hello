package com.uestc.rabbitmq.deadQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.uestc.rabbitmq.utils.RabbitMqUtils;

import java.util.HashMap;
import java.util.Map;

public class normalRev {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception{
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(NORMAL_EXCHANGE, "direct");
        channel.exchangeDeclare(DEAD_EXCHANGE, "direct");
        // 设置正常队列转发到死信队列,就在map参数里面设置
        Map<String, Object> params = new HashMap<>();
        params.put("x-dead-letter-exchange", DEAD_EXCHANGE);
        params.put("x-dead-letter-routing-key", "dead");
        //params.put("x-max-length", 6);  // 队列积压个数超过6，就会进入死信队列
        channel.queueDeclare(NORMAL_QUEUE, false, false, false, params);

        channel.queueBind(NORMAL_QUEUE, NORMAL_EXCHANGE, "normal");
        System.out.println("normal consumer is waiting...");
        DeliverCallback deliverCallback = (ConsumerTag, message)->{
            System.out.println(new String(message.getBody()));
        };
        channel.basicConsume(NORMAL_QUEUE, true,deliverCallback, ConsumerTag->{});
    }
}
