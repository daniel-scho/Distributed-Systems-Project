package com.fh.stationdatacollector;
/*
import com.fh.stationdatacollector.dto.StationData;
import com.fh.stationdatacollector.services.Database;
import com.fh.stationdatacollector.services.Queue;
*/

import queue.StationDataCollectorProducer;

import java.util.List;

public class StationDataCollector {

    private StationDataCollectorProducer queueSender;
    public StationDataCollector( StationDataCollectorProducer queueSender) {
       // this.database = database;
        this.queueSender = queueSender;
    }
/*
    private Database database;
    public int getTotalKwhForCustomer(int id) {
        List<StationData> stationData = database.getAllStationData();

        int total = stationData.stream()
                .filter(data -> data.getCustomerId() == id)
                .mapToInt(StationData::getKwh)
                .sum();
    }
*/

}
