package com.fh.stationdatacollector;

import queue.StationDataCollectorConsumer;
import queue.StationDataCollectorProducer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class StationDataCollectorApplication {

	private StationDataCollectorConsumer consume;
	private StationDataCollectorProducer produce;

	public static void main(String[] args) throws Exception {
		StationDataCollectorApplication application = new StationDataCollectorApplication();
		application.startConsuming();
		application.startProducing();
	}

	private void startConsuming() throws Exception {
		consume = new StationDataCollectorConsumer();
		consume.executeStationDataCollectorQueue();
	}

	private void startProducing() throws Exception {
		produce = new StationDataCollectorProducer();
		produce.executeDataCollectiontoReceiverQueue();
	}
}
