package controller;

import dao.TeamDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Team;
import model.User;

import java.util.List;

public class FollowTeamsController {

    @FXML
    private VBox teamContainer;

    private TeamDAO teamDAO = new TeamDAO();

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        loadFollowedTeams(); // 🔥 IMPORTANT
    }

    public void initialize() {
        // do nothing here
    }

    private void loadFollowedTeams() {

        teamContainer.getChildren().clear();

        List<Team> teams = teamDAO.getFollowedTeams(currentUser.getUserId());

        for (Team t : teams) {

            VBox card = new VBox();
            card.setSpacing(8);
            card.setStyle(
                    "-fx-background-color: #1e1e1e;" +
                            "-fx-padding: 14;" +
                            "-fx-background-radius: 12;"
            );

            Label name = new Label(t.getTeamName());
            name.setStyle(
                    "-fx-text-fill: white;" +
                            "-fx-font-size: 16px;" +
                            "-fx-font-weight: bold;"
            );

            // 🔥 CLICK → OPEN DETAILS
            card.setOnMouseClicked(e -> openTeamDetails(t));

            card.getChildren().add(name);
            teamContainer.getChildren().add(card);
        }
    }

    // 🔥 TEAM DETAILS SCREEN
    private void openTeamDetails(Team team) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/TeamDetails.fxml"));
            Scene scene = new Scene(loader.load());

            TeamDetailsController controller = loader.getController();
            controller.setData(team, currentUser);

            Stage stage = (Stage) teamContainer.getScene().getWindow();
            stage.setScene(scene);

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

            Stage stage = (Stage) teamContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}