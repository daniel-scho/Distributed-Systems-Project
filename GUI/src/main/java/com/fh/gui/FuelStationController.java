package com.fh.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import java.net.URI;
import java.io.IOException;
import java.net.URISyntaxException;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class FuelStationController {
    private String URI = "http://localhost:8080/invoices/";
    @FXML
    private Label getButtonLabel;
    @FXML
    private Label postButtonLabel;
    @FXML
    private TextField customerIdField;

    @FXML
    protected void onGetPdf() throws IOException, InterruptedException, URISyntaxException {
        String customerId = customerIdField.getText();
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URI + customerId))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String res = response.body();

        getButtonLabel.setText(res);
    }

    @FXML
    protected void sendData() throws IOException, InterruptedException, URISyntaxException {
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URI))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request,
                HttpResponse.BodyHandlers.ofString());

        String res = response.body();

        postButtonLabel.setText(res);
    }
}