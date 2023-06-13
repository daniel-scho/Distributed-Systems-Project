package dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

public class Customer {

    public Customer(@JsonProperty("id") int customer_id,
                    @JsonProperty("chargedKWH") List<Double> chargedKWH) {
        this.customer_id = customer_id;
        this.chargedKWH = chargedKWH;
    }
    @JsonProperty("customer_id")
    public int customer_id;
    @JsonProperty("chargedKWH")
    public List<Double> chargedKWH = new ArrayList<>();

    public String sumKWHAsJson() throws JsonProcessingException {
        double sum = 0;
        for (Double value : chargedKWH) {
            sum += value;
        }

        // Erstelle ein JSON-Objekt
        ObjectMapper mapper = new ObjectMapper();
        String jsonResult = mapper.writeValueAsString(sum);

        return jsonResult;
    }

    @Override
    public String toString() {
        try {
            return "Customer ID: " + customer_id +
                    "\nKWH charged list: " + chargedKWH +
                    "\nSum of the KWH = " + sumKWHAsJson();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}