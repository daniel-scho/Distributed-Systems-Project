package com.fh.stationdatacollector.services;

import com.fh.stationdatacollector.dto.Station;

import java.sql.SQLException;

public class DataCollectorService {
    public void processStation(Station station, int customerId) throws SQLException {
        Database database = new Database(station.dbURL);

        DatabaseQueryExecuter dbClient = new DatabaseQueryExecuter(database);

        dbClient.getCustomer(customerId);

        //StationDataCollectorProducer produce = new StationDataCollectorProducer();

        //produce.executeDataCollectiontoReceiverQueue();
    }
}
