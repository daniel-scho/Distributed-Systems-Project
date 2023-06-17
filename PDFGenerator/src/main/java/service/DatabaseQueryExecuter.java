package service;

import dto.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DatabaseQueryExecuter {
    private Database database;
    private Connection connection;

    public DatabaseQueryExecuter (Database database) throws SQLException {
        this.database = database;

        this.connection = database.getConnection();
    }

    public Customer getCustomerData(Customer customer) {
        String query = "SELECT * FROM customer WHERE id = ?";

        try (PreparedStatement ps = connection.prepareStatement(query)) {
            ps.setInt(1, customer.customer_id);
            try (ResultSet rs = ps.executeQuery()) {
                // If the customer does not exist return null

                if (!rs.next()) {
                    return null;
                }
                else {
                    int id = rs.getInt("id");
                    String first_name = rs.getString("first_name");
                    String last_name = rs.getString("last_name");

                    customer.first_name = first_name;
                    customer.last_name = last_name;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return customer;
    }
}
