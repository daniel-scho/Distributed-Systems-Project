package com.fh.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeoutException;

public class MessagingQueueApplication {
    private final static String QUEUE_1 = "DataCollectionDispatcher";
    private final static String QUEUE_2 = "DataCollectionDispatcher_Out";
    private final static String QUEUE_3 = "StationDataCollector_In";
    private final static String QUEUE_4 = "StationDataCollector_Out";
    private final static String QUEUE_5 = "QUEUE_5";
    private final static String QUEUE_6 = "QUEUE_6";

    private static CountDownLatch latch;

    public static void main(String[] args) throws IOException {
        System.out.println("Starting Messaging Queue");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();
             Channel publishChannel = connection.createChannel()) {

            channel.queueDeclare(QUEUE_1, false, false, false, null);
            channel.queueDeclare(QUEUE_2, false, false, false, null);
            channel.queueDeclare(QUEUE_3, false, false, false, null);
            channel.queueDeclare(QUEUE_4, false, false, false, null);
            channel.queueDeclare(QUEUE_5, false, false, false, null);
            channel.queueDeclare(QUEUE_6, false, false, false, null);

            String message = "123";
            publishMessage(publishChannel, QUEUE_1, message);


            processQueue(channel, QUEUE_2, QUEUE_3);
            latch = new CountDownLatch(1);
            try {
                latch.await(); // Wait until latch count reaches zero
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            processQueue(channel, QUEUE_4, QUEUE_5);
            latch = new CountDownLatch(1);

            try {
                latch.await(); // Wait until latch count reaches zero
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            DeliverCallback queue_3_callback = (consumerTag, delivery) -> {
                String reversedMessage = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Final Result received '" + reversedMessage + "'");
            };
            channel.basicConsume(QUEUE_6, true, queue_3_callback, consumerTag -> {});
            latch = new CountDownLatch(1);

            try {
                latch.await(); // Wait until latch count reaches zero
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println(" [x] Finished queue");

        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private static void publishMessage(Channel channel, String targetQueue, String message) throws IOException {
        channel.queueDeclare(targetQueue, false, false, false, null);
        channel.basicPublish("", targetQueue, null, message.getBytes(StandardCharsets.UTF_8));
        System.out.println(" [x] Send " + message + " to " + targetQueue);
    }

    private static void processQueue(Channel channel, String sourceQueue, String targetQueue) throws IOException {
        DeliverCallback callback = (consumerTag, delivery) -> {
            String newMessage = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + newMessage + "'");


            publishMessage(channel, targetQueue, newMessage);
            latch.countDown(); // Signal that response has been received

        };

        channel.basicConsume(sourceQueue, true, callback, consumerTag -> {});
    }
}

/*
private final static String QUEUE_1 = "QUEUE_1";
    private final static String QUEUE_2 = "QUEUE_2";
    private final static String QUEUE_3 = "QUEUE_3";



    public static void main(String[] args) throws IOException {

        System.out.println("Starting Messaging Queue");

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);


        try (
                Connection connection = factory.newConnection();
                Channel channel = connection.createChannel()
        ) {
            channel.queueDeclare(QUEUE_1, false, false, false, null);

            Scanner scanner = new Scanner(System.in);


            System.out.println(" [~] Enter text: ");
            String message = scanner.nextLine();


            channel.basicPublish("",
                    QUEUE_1, null,
                    message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Send " + message + " x3");

            callBack(channel, QUEUE_2, QUEUE_1);

            callBack(channel, QUEUE_3, QUEUE_2);

            DeliverCallback queue_3_callback = (consumerTag, delivery) -> {
                String reversedMessage = new String(delivery.getBody(), "UTF-8");
                System.out.println(" [x] Final Result received '" + reversedMessage + "'");

            };
            channel.basicConsume(QUEUE_3, true, queue_3_callback, consumerTag -> { });

        } catch (TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

    private static void callBack(Channel channel, String queue2, String queue1) throws IOException {
        DeliverCallback queue_1_callback = (consumerTag, delivery) -> {
            String newMessage = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + newMessage + "'");

            try {
                channel.queueDeclare(queue2, false, false, false, null);

                channel.basicPublish("",
                        queue2, null,
                        newMessage.getBytes(StandardCharsets.UTF_8));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
        try {
            channel.basicConsume(queue1, true, queue_1_callback, consumerTag -> {
            });
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
 */