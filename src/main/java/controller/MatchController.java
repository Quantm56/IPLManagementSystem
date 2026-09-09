package controller;

import dao.MatchDAO;
import dao.TeamDAO;
import dao.VenueDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Match;
import model.Team;
import model.User;
import model.Venue;

import java.util.List;

public class MatchController {

    @FXML
    private VBox matchContainer;

    private MatchDAO matchDAO = new MatchDAO();
    private TeamDAO teamDAO = new TeamDAO();
    private VenueDAO venueDAO = new VenueDAO();

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        loadMatches();
    }

    public void initialize() {}

    private void loadMatches() {

        matchContainer.getChildren().clear();

        List<Match> matches = matchDAO.getAllMatchesFull();

        for (Match m : matches) {

            Team t1 = teamDAO.getTeamById(m.getTeam1Id());
            Team t2 = teamDAO.getTeamById(m.getTeam2Id());
            Venue v = venueDAO.getAllVenues()
                    .stream()
                    .filter(x -> x.getVenueId() == m.getVenueId())
                    .findFirst()
                    .orElse(null);

            // 🔳 CARD
            VBox card = new VBox();
            card.setSpacing(10);
            card.setStyle(
                    "-fx-background-color: #1e1e1e;" +
                            "-fx-padding: 16;" +
                            "-fx-background-radius: 14;" +
                            "-fx-border-color: #2a2a2a;" +
                            "-fx-border-radius: 14;"
            );

            // 🆚 MATCH TITLE (BIG)
            Label title = new Label(t1.getTeamName() + " vs " + t2.getTeamName());
            title.setStyle(
                    "-fx-text-fill: white;" +
                            "-fx-font-size: 18px;" +
                            "-fx-font-weight: bold;"
            );

            // 📅 DATE
            Label date = new Label("📅 " + m.getMatchDate());
            date.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");

            // 📍 VENUE
            Label venue = new Label("📍 " + (v != null ? v.getVenueName() : "Unknown Venue"));
            venue.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 13px;");

            // 🟢 STATUS
            Label status = new Label("Status: " + m.getStatus());

            String color = "#00c853"; // green default
            if (m.getStatus().equals("COMPLETED")) color = "#2979ff";
            if (m.getStatus().equals("CANCELLED")) color = "#ff1744";

            status.setStyle(
                    "-fx-text-fill: " + color + ";" +
                            "-fx-font-size: 13px;" +
                            "-fx-font-weight: bold;"
            );

            card.getChildren().addAll(title, date, venue, status);
            matchContainer.getChildren().add(card);
        }
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Feed.fxml"));
            Scene scene = new Scene(loader.load());

            FeedController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) matchContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}