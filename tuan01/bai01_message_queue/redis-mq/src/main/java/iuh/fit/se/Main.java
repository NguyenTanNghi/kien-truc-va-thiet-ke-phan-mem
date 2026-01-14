package iuh.fit.se;

import iuh.fit.se.consumer.MessageConsumer;
import iuh.fit.se.producer.MessageProducer;

public class Main {

    public static void main(String[] args) {

        // Chạy consumer ở thread riêng
        new Thread(() -> {
            MessageConsumer consumer = new MessageConsumer();
            consumer.start();
        }).start();

        // Producer
        MessageProducer producer = new MessageProducer();

        for (int i = 1; i <= 5; i++) {
            producer.sendMessage("Hello Redis MQ - " + i);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
