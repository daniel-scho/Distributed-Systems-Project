package com.fh.gui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.*;
import java.net.URI;
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
    private Label notificationLabel;

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
        HttpResponse<byte[]> response = client.send(request, HttpResponse.BodyHandlers.ofByteArray());

        if (response.statusCode() == 404) {
            notificationLabel.setText("404 Not found");
        } else {
            // Source:
            // To view PDF with external tool:
            // https://javabeginners.de/Ein-_und_Ausgabe/PDF_anzeigen.php
            // ------------------------
            String fileName = "Invoice.pdf";
            String projectDirectory = System.getProperty("user.dir");
            String filePath = projectDirectory + File.separator + fileName;
            File pdfFile = new File(filePath);

            notificationLabel.setText("Invoice found!" +
                    "\nSaved on: " + pdfFile +
                    "\nOpening file...");

            try (OutputStream outputStream = new FileOutputStream(pdfFile)) {
                byte[] pdfBytes = response.body();
                outputStream.write(pdfBytes);
            }

            try {
                Desktop desktop = Desktop.getDesktop();
                if (desktop.isSupported(Desktop.Action.OPEN)) {
                    desktop.open(pdfFile);
                } else {
                    System.err.println("Could not find PDF File");
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            getPDFTimeline.stop();
        }
    }

    @FXML
    protected void sendData() throws IOException, InterruptedException, URISyntaxException {
        notificationLabel.setText("");
        if (getPDFTimeline != null) {
            getPDFTimeline.stop();
        }

        String customerId = customerIdField.getText();
        if (!customerId.matches("\\d*")) {
            notificationLabel.setText("Customer ID must be a number!");
            return;
        }

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

        getPDFTimeline = new Timeline(new KeyFrame(Duration.seconds(4), (ActionEvent event) -> {
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
