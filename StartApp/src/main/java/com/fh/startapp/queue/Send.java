package com.fh.startapp.queue;


import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.Channel;
import org.springframework.stereotype.Component;

@Component
public class Send {
    private final String QUEUE_DCD = "DataCollectionDispatcher";

    public void executeSendQueue(String customerId) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(QUEUE_DCD, false, false, false, null);

        String message = customerId;

        channel.basicPublish("", QUEUE_DCD, null, customerId.getBytes());
        System.out.println(" [x] Sent '" + message + "' to Queue: " + QUEUE_DCD);

        channel.close();
        connection.close();
    }
}