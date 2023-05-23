package com.fh.startapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@SpringBootApplication
public class StartAppApplication {

    public static void main(String[] args) {

        SpringApplication.run(StartAppApplication.class, args);


        //String query = "SELECT * FROM customer WHERE id = ?";
        String query = "SELECT * FROM customer";

        try (
            Connection conn = Database.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();
        ) {
            //ps.setInt(1, 1);
            while (rs.next()) {
                int id = rs.getInt("id");
                String firstName = rs.getString("first_name");

                String lastName = rs.getString("last_name");

                System.out.println(String.format(
                        "%s: %s %s%n",
                        id, firstName, lastName
                ));

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }


        ////////////////////// Message quee

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("[x] Finished " + message);
        };
    }
}
