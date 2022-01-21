package com.uestc.rabbitmq.utils;

public class SleepUtils {
    public static void sleep(int seconds){
        try {
            Thread.sleep(1000*seconds);
        }catch (InterruptedException _ignored){
            Thread.currentThread().interrupt();
        }
    }
}
