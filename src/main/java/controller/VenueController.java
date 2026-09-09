package controller;

import dao.VenueDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.User;
import model.Venue;

import java.awt.Desktop;
import java.net.URI;
import java.util.List;

public class VenueController {

    @FXML
    private VBox venueContainer;

    private VenueDAO dao = new VenueDAO();

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    public void initialize() {
        loadVenues();
    }

    private void loadVenues() {

        venueContainer.getChildren().clear();

        List<Venue> list = dao.getAllVenues();

        for (Venue v : list) {

            // 🔳 CARD
            VBox card = new VBox();
            card.setSpacing(8);
            card.setStyle(
                    "-fx-background-color: #1e1e1e;" +
                            "-fx-padding: 16;" +
                            "-fx-background-radius: 14;" +
                            "-fx-border-color: #2a2a2a;" +
                            "-fx-border-radius: 14;"
            );

            // 🏟 NAME (BIG)
            Label name = new Label(v.getVenueName());
            name.setStyle(
                    "-fx-text-fill: white;" +
                            "-fx-font-size: 18px;" +
                            "-fx-font-weight: bold;"
            );

            // 📍 DETAILS (SMALL)
            Label details = new Label(
                    v.getCity() + " • Capacity: " + v.getCapacity() + "\n" +
                            "Lat: " + v.getLatitude() + " | Long: " + v.getLongitude()
            );
            details.setStyle(
                    "-fx-text-fill: #aaaaaa;" +
                            "-fx-font-size: 13px;"
            );

            // 🌍 MAP BUTTON
            Button mapBtn = new Button("View on Map");

            mapBtn.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-border-color: #00c853;" +
                            "-fx-text-fill: #00c853;" +
                            "-fx-border-radius: 20;" +
                            "-fx-background-radius: 20;"
            );

            mapBtn.setOnAction(e -> openMap(v.getLatitude(), v.getLongitude()));

            card.getChildren().addAll(name, details, mapBtn);
            venueContainer.getChildren().add(card);
        }
    }

    // 🌍 GOOGLE MAP OPEN
    private void openMap(double lat, double lng) {
        try {
            String url = "https://www.google.com/maps?q=" + lat + "," + lng;

            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().browse(new URI(url));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Feed.fxml"));
            Scene scene = new Scene(loader.load());

            FeedController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) venueContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}