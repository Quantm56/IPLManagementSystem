package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

public class AdminDashboardController {

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void openTeams() {
        open("/view/ManageTeams.fxml");
    }

    @FXML
    private void openVenues() {
        open("/view/ManageVenues.fxml");
    }

    @FXML
    private void openMatches() {
        open("/view/ScheduleMatch.fxml");
    }

    @FXML
    private void openResults() {
        open("/view/EnterResult.fxml");
    }

    @FXML
    private void logout() {
        open("/view/Login.fxml");
    }

    // 🔥 FIXED NAVIGATION
    private void open(String fxml) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxml));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) javafx.stage.Stage.getWindows()
                    .filtered(w -> w.isShowing())
                    .get(0);

            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}