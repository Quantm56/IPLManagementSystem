package controller;

import dao.TeamDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Team;

import java.util.List;

public class ManageTeamsController {

    @FXML private VBox teamContainer;

    @FXML private TextField teamIdField;
    @FXML private TextField teamNameField;
    @FXML private TextField coachField;
    @FXML private TextField captainField;
    @FXML private TextField venueIdField;

    @FXML private Label statusLabel;

    private TeamDAO dao = new TeamDAO();

    @FXML
    public void initialize() {
        loadTeams();
    }

    // ✅ ADD TEAM
    @FXML
    private void handleAddTeam() {

        try {
            int id = Integer.parseInt(teamIdField.getText());
            String name = teamNameField.getText();
            String coach = coachField.getText();
            String captain = captainField.getText();
            int venueId = Integer.parseInt(venueIdField.getText());

            Team t = new Team(id, name, coach, captain, venueId);
            dao.addTeam(t);

            statusLabel.setText("Team added successfully");

            teamIdField.clear();
            teamNameField.clear();
            coachField.clear();
            captainField.clear();
            venueIdField.clear();

            loadTeams();

        } catch (Exception e) {
            statusLabel.setText("Error: Check inputs / FK constraint");
            e.printStackTrace();
        }
    }

    // ✅ LOAD TEAMS UI
    private void loadTeams() {

        teamContainer.getChildren().clear();

        List<Team> teams = dao.getAllTeams();

        for (Team t : teams) {

            // 🔥 TEAM NAME (BIG)
            Label name = new Label(t.getTeamName());
            name.setStyle("-fx-text-fill:white; -fx-font-size:18px; -fx-font-weight:bold;");

            // 🔥 DETAILS (UPDATED WITH TEAM ID)
            Label details = new Label(
                    "ID: " + t.getTeamId() +
                            "   •   Coach: " + t.getCoach() +
                            "   •   Captain: " + t.getCaptain() +
                            "   •   Venue: " + t.getHomeVenueId()
            );
            details.setStyle("-fx-text-fill:#aaaaaa; -fx-font-size:13px;");

            // 🔥 DELETE BUTTON
            Button deleteBtn = new Button("🗑");
            deleteBtn.setStyle(
                    "-fx-background-color:#ff1744;" +
                            "-fx-text-fill:white;" +
                            "-fx-font-size:14px;" +
                            "-fx-background-radius:20;" +
                            "-fx-min-width:35;" +
                            "-fx-min-height:35;"
            );

            deleteBtn.setOnAction(e -> {
                dao.deleteTeam(t.getTeamId());
                loadTeams();
            });

            // 🔥 PUSH DELETE BUTTON TO RIGHT
            Region spacer = new Region();
            HBox.setHgrow(spacer, Priority.ALWAYS);

            HBox topRow = new HBox(name, spacer, deleteBtn);
            topRow.setSpacing(10);

            VBox card = new VBox(6, topRow, details);
            card.setStyle(
                    "-fx-background-color:#1a1a1a;" +
                            "-fx-padding:15;" +
                            "-fx-background-radius:12;" +
                            "-fx-border-color:#2a2a2a;" +
                            "-fx-border-radius:12;"
            );

            teamContainer.getChildren().add(card);
        }
    }

    // ✅ BACK
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) teamContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}