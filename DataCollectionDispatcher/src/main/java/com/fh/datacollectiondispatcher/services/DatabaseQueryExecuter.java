package com.fh.datacollectiondispatcher.services;

import com.fh.datacollectiondispatcher.dto.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryExecuter {
    private Database database;
    private Connection connection;

    public DatabaseQueryExecuter (Database database) throws SQLException {
        this.database = database;

        this.connection = database.getConnection();
    }

    public List<Station> getAllStations() {
        String query = "SELECT * FROM station";
        List<Station> stations = new ArrayList<>();

        try (
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                String dbUrl = rs.getString("db_url");
                double lat = rs.getDouble("lat");
                double lng = rs.getDouble("lng");
                Station station = new Station(id, dbUrl, lat, lng);

                stations.add(station);
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return stations;
    }
}
