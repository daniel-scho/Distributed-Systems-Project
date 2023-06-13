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

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.util.Duration;

public class FuelStationController {
    private String URI = "http://localhost:8080/invoices/";
    private Timeline getPDFTimeline;

    @FXML
    private Label getButtonLabel;

    @FXML
    private TextField customerIdField;

    @FXML
    protected void getPDF() throws IOException, InterruptedException, URISyntaxException {
        String customerId = customerIdField.getText();
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URI + customerId))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String res = response.body();

        getButtonLabel.setText(res);


    }

    @FXML
    protected void sendData() throws IOException, InterruptedException, URISyntaxException {
        String customerId = customerIdField.getText();
        HttpClient client = HttpClient.newHttpClient();

        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(URI + customerId))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        String res = response.body();

        if (getPDFTimeline != null) {
            getPDFTimeline.stop();
        }

        getPDFTimeline = new Timeline(new KeyFrame(Duration.seconds(5), (ActionEvent event) -> {
            try {
                getPDF();
            } catch (IOException | InterruptedException | URISyntaxException e) {
                e.printStackTrace();
            }
        }));

        getPDFTimeline.setCycleCount(Timeline.INDEFINITE);
        getPDFTimeline.play();
    }
}
