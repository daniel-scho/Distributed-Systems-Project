package com.fh.stationdatacollector;

import com.fh.stationdatacollector.dto.StationData;
import com.fh.stationdatacollector.services.Database;
import com.fh.stationdatacollector.services.Queue;

import java.util.List;

public class StationDataCollector {

    private Database database;
    private Queue queue;
    public StationDataCollector(Database database, Queue queue) {
        this.database = database;
        this.queue = queue;
    }

    public int getTotalKwhForCustomer(int id) {
        List<StationData> stationData = database.getAllStationData();

        int total = stationData.stream()
                .filter(data -> data.getCustomerId() == id)
                .mapToInt(StationData::getKwh)
                .sum();
    }


}
