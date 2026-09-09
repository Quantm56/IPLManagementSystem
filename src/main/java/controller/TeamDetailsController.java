package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.Team;
import model.User;

public class TeamDetailsController {

    @FXML private Label teamName;
    @FXML private Label coach;
    @FXML private Label captain;

    private User currentUser;

    public void setData(Team team, User user) {
        this.currentUser = user;

        teamName.setText(team.getTeamName());
        coach.setText("Coach: " + team.getCoach());
        captain.setText("Captain: " + team.getCaptain());
    }

    @FXML
    private void goBack() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FollowTeams.fxml"));
            Scene scene = new Scene(loader.load());

            FollowTeamsController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) teamName.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}