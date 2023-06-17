package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    public Customer(@JsonProperty("id") int customer_id,
                    @JsonProperty("chargedKWH") List<Double> chargedKWH) {
        this.customer_id = customer_id;
        this.chargedKWH = chargedKWH;
    }
    public Customer() {

    }

    @JsonProperty("customer_id")
    public int customer_id;

    public String first_name;

    public String last_name;

    @JsonProperty("chargedKWH")
    public List<Double> chargedKWH = new ArrayList<>();

    public String calculateSumOfKWH() {
        double sum = 0;
        for (Double value : chargedKWH) {
            sum += value;
        }
        // round the value and make it a string
        return String.valueOf(Math.round(sum * 100.0) / 100.0);
    }

    @Override
    public String toString() {
        String space = "                                ";
        StringBuilder chargedKWHBuilder = new StringBuilder();
        for (Double kwh : chargedKWH) {
            chargedKWHBuilder.append(space + kwh).append("\n");
        }

        return "Customer ID: " + customer_id +
                "\nName: " + first_name + " " + last_name +
                "\nKWH charged: \n" +
                chargedKWHBuilder.toString() +
                space + "-------" +
                "\nSum of the KWH = " + calculateSumOfKWH();

    }
}