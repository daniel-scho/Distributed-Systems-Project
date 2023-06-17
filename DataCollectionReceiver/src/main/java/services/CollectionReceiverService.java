package services;

import dto.Customer;
import queue.DataCollectionReceiverConsumer;
import queue.DataCollectionReceiverProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class CollectionReceiverService {

    private final DataCollectionReceiverConsumer consumerQueue = new DataCollectionReceiverConsumer(this);
    private static final DataCollectionReceiverProducer producerQueue = new DataCollectionReceiverProducer();

    public CollectionReceiverService() throws IOException, TimeoutException {
    }


    public void executeQueue() throws Exception {
        consumerQueue.consumeMessagesJob();
    }

    public void sendToPDFGenerator(Customer customer) throws Exception {
        producerQueue.sendToPDFGenerator(customer);
    }


}