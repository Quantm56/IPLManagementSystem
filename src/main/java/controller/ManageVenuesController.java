package controller;

import dao.VenueDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import model.Venue;

import java.util.List;

public class ManageVenuesController {

    @FXML private VBox venueContainer;

    @FXML private TextField venueIdField;
    @FXML private TextField venueNameField;
    @FXML private TextField cityField;
    @FXML private TextField capacityField;
    @FXML private TextField latitudeField;
    @FXML private TextField longitudeField;

    @FXML private Label statusLabel;

    private VenueDAO dao = new VenueDAO();

    @FXML
    public void initialize() {
        loadVenues();
    }

    // ✅ LOAD VENUES (MODERN UI)
    private void loadVenues() {

        venueContainer.getChildren().clear();
        List<Venue> list = dao.getAllVenues();

        for (Venue v : list) {

            VBox card = new VBox();
            card.setSpacing(6);
            card.setStyle(
                    "-fx-background-color: #1e1e1e;" +
                            "-fx-padding: 15;" +
                            "-fx-background-radius: 12;" +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.6), 10, 0, 0, 3);"
            );

            // 🔥 BIG TITLE
            Label title = new Label(v.getVenueName());
            title.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

            // 📄 DETAILS
            Label details = new Label(
                    "City: " + v.getCity() +
                            " | Capacity: " + v.getCapacity() +
                            " | ID: " + v.getVenueId()
            );
            details.setStyle("-fx-text-fill: #bbbbbb; -fx-font-size: 12px;");

            // 🔴 DELETE BUTTON
            Button deleteBtn = new Button("Delete");
            deleteBtn.setStyle(
                    "-fx-background-color: linear-gradient(to right, #ff416c, #ff4b2b);" +
                            "-fx-text-fill: white;" +
                            "-fx-background-radius: 8;"
            );

            deleteBtn.setOnAction(e -> {
                dao.deleteVenue(v.getVenueId());
                loadVenues();
            });

            HBox bottom = new HBox(deleteBtn);
            bottom.setSpacing(10);

            card.getChildren().addAll(title, details, bottom);
            venueContainer.getChildren().add(card);
        }
    }

    // ✅ ADD VENUE (WITH ALL FIELDS)
    @FXML
    private void handleAddVenue() {

        try {
            int id = Integer.parseInt(venueIdField.getText());
            String name = venueNameField.getText();
            String city = cityField.getText();
            int capacity = Integer.parseInt(capacityField.getText());
            double lat = Double.parseDouble(latitudeField.getText());
            double lon = Double.parseDouble(longitudeField.getText());

            Venue v = new Venue(id, name, city, capacity, lat, lon);

            dao.addVenueFull(v);

            statusLabel.setText("Venue added successfully");

            venueIdField.clear();
            venueNameField.clear();
            cityField.clear();
            capacityField.clear();
            latitudeField.clear();
            longitudeField.clear();

            loadVenues();

        } catch (Exception e) {
            statusLabel.setText("Invalid input!");
            e.printStackTrace();
        }
    }

    // ✅ BACK
    @FXML
    private void goBack() {
        try {
            Stage stage = (Stage) venueContainer.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
            Scene scene = new Scene(loader.load());

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}