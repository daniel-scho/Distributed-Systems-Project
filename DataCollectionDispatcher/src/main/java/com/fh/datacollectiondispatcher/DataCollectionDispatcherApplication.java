package com.fh.datacollectiondispatcher;

import org.flywaydb.core.Flyway;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DataCollectionDispatcherApplication {

	public static void main(String[] args) {
		Flyway flyway = Flyway.configure()
				.load();
		flyway.migrate();

		SpringApplication.run(DataCollectionDispatcherApplication.class, args);
	}

}
