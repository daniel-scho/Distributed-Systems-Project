package com.fh.stationdatacollector.services;

import com.fh.stationdatacollector.dto.Customer;
import com.fh.stationdatacollector.dto.Station;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseQueryExecuter {
    private Connection connection;
    public DatabaseQueryExecuter (Database database) throws SQLException {

        this.connection = database.getConnection();
    }

    public Customer getCustomer(int customerId) {
        String query = "SELECT * FROM charge where customer_id = " + customerId;
        Customer customer = new Customer(customerId);

        try (
            PreparedStatement ps = connection.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        ) {
            while (rs.next()) {
                int id = rs.getInt("id");
                double kwh = rs.getDouble("kwh");
                // int id = rs.getInt("customer_id");

                customer.chargedKWH.add(kwh);
            }
            System.out.println(customer.chargedKWH);

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }
}