package com.fh.startapp.queue;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {
    private final static String QUEUE_toDispatcher = "DataCollectionDispatcherQueue";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_toDispatcher, false, false, false, null);

        String customerId = "123456"; // Kunden-ID
        String message = "Start Customer ID :" + customerId; //Startnachricht

        channel.basicPublish("", QUEUE_toDispatcher, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}