package controller;

import dao.MatchDAO;
import dao.TeamDAO;
import dao.VenueDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Match;
import model.Team;
import model.Venue;

import java.time.LocalDate;
import java.util.List;

public class ScheduleMatchController {

    @FXML private ComboBox<Team> team1Box;
    @FXML private ComboBox<Team> team2Box;
    @FXML private ComboBox<Venue> venueBox;
    @FXML private DatePicker matchDatePicker;
    @FXML private Label statusLabel;

    private TeamDAO teamDAO = new TeamDAO();
    private VenueDAO venueDAO = new VenueDAO();
    private MatchDAO matchDAO = new MatchDAO();

    @FXML
    public void initialize() {
        loadData();
    }

    // ✅ LOAD TEAMS & VENUES
    private void loadData() {

        List<Team> teams = teamDAO.getAllTeams();
        List<Venue> venues = venueDAO.getAllVenues();

        team1Box.getItems().addAll(teams);
        team2Box.getItems().addAll(teams);
        venueBox.getItems().addAll(venues);

        // show names instead of object hash
        team1Box.setCellFactory(cb -> new ListCell<>() {
            protected void updateItem(Team t, boolean empty) {
                super.updateItem(t, empty);
                setText(empty ? "" : t.getTeamName());
            }
        });

        team2Box.setCellFactory(cb -> new ListCell<>() {
            protected void updateItem(Team t, boolean empty) {
                super.updateItem(t, empty);
                setText(empty ? "" : t.getTeamName());
            }
        });

        venueBox.setCellFactory(cb -> new ListCell<>() {
            protected void updateItem(Venue v, boolean empty) {
                super.updateItem(v, empty);
                setText(empty ? "" : v.getVenueName());
            }
        });

        team1Box.setButtonCell(team1Box.getCellFactory().call(null));
        team2Box.setButtonCell(team2Box.getCellFactory().call(null));
        venueBox.setButtonCell(venueBox.getCellFactory().call(null));
    }

    // ✅ HANDLE SCHEDULE MATCH
    @FXML
    private void handleSchedule() {

        try {
            Team t1 = team1Box.getValue();
            Team t2 = team2Box.getValue();
            Venue v = venueBox.getValue();
            LocalDate date = matchDatePicker.getValue();

            if (t1 == null || t2 == null || v == null || date == null) {
                statusLabel.setText("Fill all fields");
                return;
            }

            if (t1.getTeamId() == t2.getTeamId()) {
                statusLabel.setText("Teams cannot be same");
                return;
            }

            Match match = new Match(
                    0,
                    t1.getTeamId(),
                    t2.getTeamId(),
                    v.getVenueId(),
                    java.sql.Date.valueOf(date),
                    "SCHEDULED"
            );

            matchDAO.addMatch(match);

            statusLabel.setText("Match Scheduled Successfully");

            team1Box.setValue(null);
            team2Box.setValue(null);
            venueBox.setValue(null);
            matchDatePicker.setValue(null);

        } catch (Exception e) {
            statusLabel.setText("Error scheduling match");
            e.printStackTrace();
        }
    }

    // ✅ BACK BUTTON
    @FXML
    private void goBack() {
        try {
            Stage stage = (Stage) team1Box.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
            Scene scene = new Scene(loader.load());

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}