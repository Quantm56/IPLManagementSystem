package controller;

import dao.MatchResultDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.MatchResult;
import model.User;

import java.util.List;

public class ResultController {

    @FXML
    private VBox resultContainer;

    private MatchResultDAO dao = new MatchResultDAO();

    private User currentUser;

    // ✅ receive user
    public void setUser(User user) {
        this.currentUser = user;
    }

    public void initialize() {

        List<MatchResult> list = dao.getAllResults();

        for (MatchResult r : list) {

            Label label = new Label(
                    "Winner Team ID: " + r.getWinnerTeamId() +
                            " | " + r.getResultText()
            );

            label.setStyle("-fx-text-fill: white; -fx-font-size: 14px;");

            resultContainer.getChildren().add(label);
        }
    }

    // 🔥 BACK BUTTON
    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Feed.fxml"));
            Scene scene = new Scene(loader.load());

            FeedController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) resultContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}