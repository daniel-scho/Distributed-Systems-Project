package dto;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty("chargedKWH")
    public List<Double> chargedKWH = new ArrayList<>();


}