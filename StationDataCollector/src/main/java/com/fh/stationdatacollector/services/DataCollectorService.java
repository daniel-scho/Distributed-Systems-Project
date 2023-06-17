package com.fh.stationdatacollector.services;

import com.fh.stationdatacollector.dto.Customer;
import com.fh.stationdatacollector.dto.Station;
import queue.StationDataCollectorProducer;

public class DataCollectorService {
    public void processStation(Station station) throws Exception {
        Database database = new Database(station.dbURL);

        DatabaseQueryExecuter dbClient = new DatabaseQueryExecuter(database);

        Customer customer = dbClient.getCustomer(station.customer_id);

        StationDataCollectorProducer producer = new StationDataCollectorProducer();

        producer.sendToDataCollectionReceiver(customer);
    }
}