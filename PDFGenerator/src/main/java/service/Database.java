package service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public final static String DRIVER = "postgresql";
    public final static String HOST = "localhost";
    public final static int PORT = 30001;
    public final static String USERNAME = "postgres";
    public final static String PASSWORD = "postgres";
    public final static String DATABASE_NAME = "customerdb";

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(getURL());
    }

    private static String getURL() {
        // jdbc:DRIVER://HOST:PORT/DATABASE_NAME?user=USERNAME&password=PASSWORD
        //return "jdbc." + this.DRIVER +
        return String.format(
                "jdbc:%s://%s:%s/%s?user=%s&password=%s",
                DRIVER,
                HOST,
                PORT,
                DATABASE_NAME,
                USERNAME,
                PASSWORD
        );
    }
}

