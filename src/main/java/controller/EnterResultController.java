package controller;

import dao.MatchDAO;
import dao.MatchResultDAO;
import dao.TeamDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Match;
import model.MatchResult;
import model.Team;

import java.util.List;

public class EnterResultController {

    @FXML private ComboBox<Match> matchBox;
    @FXML private ComboBox<Team> winnerBox;
    @FXML private TextArea resultField;
    @FXML private Label statusLabel;

    private MatchDAO matchDAO = new MatchDAO();
    private MatchResultDAO matchResultDAO = new MatchResultDAO();
    private TeamDAO teamDAO = new TeamDAO();

    @FXML
    public void initialize() {
        loadMatches();
        setupMatchSelectionListener();
        setupUI();
    }

    // ================= LOAD MATCHES =================
    private void loadMatches() {
        matchBox.getItems().clear();

        List<Match> matches = matchDAO.getAllMatchesFull();
        matchBox.getItems().addAll(matches);

        // 🔥 Custom display for match dropdown
        matchBox.setCellFactory(cb -> new ListCell<>() {
            @Override
            protected void updateItem(Match m, boolean empty) {
                super.updateItem(m, empty);
                if (empty || m == null) {
                    setText(null);
                } else {
                    setText("Match " + m.getMatchId() +
                            " (" + m.getTeam1Id() + " vs " + m.getTeam2Id() + ")");
                }
            }
        });

        matchBox.setButtonCell(new ListCell<>() {
            @Override
            protected void updateItem(Match m, boolean empty) {
                super.updateItem(m, empty);
                if (empty || m == null) {
                    setText("Select Match");
                } else {
                    setText("Match " + m.getMatchId() +
                            " (" + m.getTeam1Id() + " vs " + m.getTeam2Id() + ")");
                }
            }
        });
    }

    // ================= MATCH SELECTION LOGIC =================
    private void setupMatchSelectionListener() {

        matchBox.setOnAction(e -> {

            Match selectedMatch = matchBox.getValue();

            if (selectedMatch == null) return;

            winnerBox.getItems().clear();

            // 🔥 ONLY 2 TEAMS
            Team team1 = teamDAO.getTeamById(selectedMatch.getTeam1Id());
            Team team2 = teamDAO.getTeamById(selectedMatch.getTeam2Id());

            winnerBox.getItems().addAll(team1, team2);
        });
    }

    // ================= UI STYLING =================
    private void setupUI() {

        // ComboBox styling
        matchBox.setStyle("-fx-background-color:#1e1e1e; -fx-text-fill:white;");
        winnerBox.setStyle("-fx-background-color:#1e1e1e; -fx-text-fill:white;");

        // TextArea styling
        resultField.setStyle(
                "-fx-control-inner-background:#1e1e1e;" +
                        "-fx-text-fill:white;" +
                        "-fx-background-radius:10;"
        );
    }

    // ================= SAVE RESULT =================
    @FXML
    private void handleSaveResult() {

        try {
            Match selectedMatch = matchBox.getValue();
            Team winner = winnerBox.getValue();
            String resultText = resultField.getText();

            if (selectedMatch == null || winner == null || resultText == null || resultText.isEmpty()) {
                statusLabel.setText("Fill all fields");
                return;
            }

            MatchResult result = new MatchResult(
                    selectedMatch.getMatchId(),
                    winner.getTeamId(),
                    resultText
            );

            matchResultDAO.addMatchResult(result);

            matchDAO.updateMatchStatus(selectedMatch.getMatchId(), "COMPLETED");

            statusLabel.setText("Result saved successfully");

            matchBox.setValue(null);
            winnerBox.setValue(null);
            resultField.clear();

        } catch (Exception e) {
            statusLabel.setText("Error saving result");
            e.printStackTrace();
        }
    }

    // ================= BACK =================
    @FXML
    private void goBack() {

        try {
            Stage stage = (Stage) matchBox.getScene().getWindow();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
            Scene scene = new Scene(loader.load());

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}