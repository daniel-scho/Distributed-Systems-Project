package com.fh.datacollectiondispatcher;

import com.fh.datacollectiondispatcher.services.Database;
import com.fh.datacollectiondispatcher.services.Queue;

import java.io.IOException;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class DataCollectionDispatcherApplication {

	public static void main(String[] args) throws IOException, TimeoutException, SQLException {
		Database db = new Database();
		Queue queue = new Queue(db);

		while(true) {
			queue.executeQueue();
		}
	}
}
