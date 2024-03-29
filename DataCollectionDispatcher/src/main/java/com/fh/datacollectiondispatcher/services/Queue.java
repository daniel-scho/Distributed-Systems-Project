package com.fh.datacollectiondispatcher.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fh.datacollectiondispatcher.dto.Station;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.TimeoutException;

public class Queue {
    private final static String QUEUE_IN = "DataCollectionDispatcher";
    private final static String QUEUE_SDC = "StationDataCollector";
    private final static String QUEUE_DCR = "DataCollectionReceiverFromDispatcher";

    private DatabaseQueryExecuter db;
    public Queue(Database database) throws SQLException {
        this.db = new DatabaseQueryExecuter(database);
    }

    public void executeQueue() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(30003);

        Connection connection = factory.newConnection();
        Channel consumeChannel = connection.createChannel();
        Channel publishChannelSDC = connection.createChannel();
        Channel publishChannelDCR = connection.createChannel();

        consumeChannel.queueDeclare(QUEUE_IN, false, false, false, null);
        publishChannelSDC.queueDeclare(QUEUE_SDC, true, false, false, null);
        publishChannelDCR.queueDeclare(QUEUE_DCR, false, false, false, null);

        System.out.println(" [* Data Collection Dispatcher Service *] Waiting for messages");

        while(true) {
            com.rabbitmq.client.Consumer handleMessage = new DefaultConsumer(consumeChannel) {

                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
                        throws java.io.IOException {
                    String customer_id = new String(body, "UTF-8");
                    System.out.println(" [x] Received '" + customer_id + "'");
                    List<Station> stations = db.getAllStations();
                    int stationsAmount = stations.size();

                    ObjectMapper mapper = new ObjectMapper();

                    for (Station station : stations) {
                        station.customer_id = Integer.parseInt(customer_id); // The customer_id gets added to the station so StationDataCollector has the customer_id
                        String json = mapper.writeValueAsString(station);

                        publishChannelSDC.basicPublish("", QUEUE_SDC, MessageProperties.PERSISTENT_BASIC, json.getBytes());
                        String entry = String.format(" [x] Sent: \n" +
                                        "   ID: %d, DB URL: %s, Latitude: %.6f, Longitude: %.6f to " + QUEUE_SDC,
                                station.id, station.dbURL, station.lat, station.lng);
                        System.out.println(entry);

                    }
                    String sendMessage = Integer.toString(stationsAmount);

                    publishChannelDCR.basicPublish("", QUEUE_DCR, null, sendMessage.getBytes(StandardCharsets.UTF_8));
                    System.out.println(" [x] Sent '" + sendMessage + "' to " + QUEUE_DCR);

                };
            };
            consumeChannel.basicConsume(QUEUE_IN, true, handleMessage);
        }
    }
}
