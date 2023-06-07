package com.fh.stationdatacollector.services;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public final static String DRIVER = "postgresql";
    public static String HOST_PORT;
    public final static String USERNAME = "postgres";
    public final static String PASSWORD = "postgres";
    public final static String DATABASE_NAME = "stationdb";

    public Database(String HOST_PORT) {
        this.HOST_PORT = HOST_PORT;
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getURL());
    }

    private static String getURL() {
        // jdbc:DRIVER://HOST:PORT/DATABASE_NAME?user=USERNAME&password=PASSWORD
        // return "jdbc." + this.DRIVER +
        return String.format(
                "jdbc:%s://%s/%s?user=%s&password=%s",
                DRIVER,
                HOST_PORT,
                DATABASE_NAME,
                USERNAME,
                PASSWORD
        );
    }
}

