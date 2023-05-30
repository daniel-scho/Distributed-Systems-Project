package com.fh.startapp.queue;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;

public class Send {
    private final String QUEUE_toDispatcher = "DataCollectionDispatcherQueue";

    public void executeSendQueue(String customerId) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_toDispatcher, false, false, false, null);

        String message = "Start Customer ID :" + customerId; //Startnachricht

        channel.basicPublish("", QUEUE_toDispatcher, null, customerId.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}