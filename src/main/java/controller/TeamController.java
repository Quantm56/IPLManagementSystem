package controller;

import dao.TeamDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Team;
import model.User;

import java.util.List;

public class TeamController {

    @FXML
    private VBox teamContainer;

    private TeamDAO dao = new TeamDAO();

    private User currentUser;

    private List<Integer> bestTeamIds;

    public void setUser(User user) {
        this.currentUser = user;

        bestTeamIds = dao.getBestTeamIds(); // already exists

        loadTeams();
    }

    public void initialize() {}

    private void loadTeams() {

        teamContainer.getChildren().clear();

        List<Team> list = dao.getAllTeams();

        for (Team t : list) {

            HBox card = new HBox();
            card.setSpacing(20);
            card.setStyle(
                    "-fx-background-color: #1e1e1e;" +
                            "-fx-padding: 15;" +
                            "-fx-background-radius: 12;"
            );

            // 🔥 LEFT SIDE (NAME + FOLLOWERS)
            VBox left = new VBox();
            left.setSpacing(5);

            Label name = new Label(t.getTeamName());
            name.setStyle("-fx-text-fill: white; -fx-font-size: 16px;");

            // 🔥 NEW → FOLLOWERS
            int followers = dao.getTeamFollowers(t.getTeamId());

            Label followerLabel = new Label("Followers: " + followers);
            followerLabel.setStyle("-fx-text-fill: #aaaaaa; -fx-font-size: 12px;");

            left.getChildren().addAll(name, followerLabel);

            // 🔥 TROPHY (RIGHT SIDE)
            Label trophy = new Label("");

            if (bestTeamIds.contains(t.getTeamId())) {
                trophy.setText("🏆");
                trophy.setStyle("-fx-font-size: 18px;");
            }

            // 🔥 FOLLOW BUTTON
            Button followBtn = new Button();

            boolean isFollowing = dao.isFollowing(currentUser.getUserId(), t.getTeamId());
            styleButton(followBtn, isFollowing);

            followBtn.setOnAction(e -> {

                boolean currentlyFollowing = dao.isFollowing(
                        currentUser.getUserId(),
                        t.getTeamId()
                );

                if (currentlyFollowing) {
                    dao.unfollowTeam(currentUser.getUserId(), t.getTeamId());
                    styleButton(followBtn, false);
                } else {
                    dao.followTeam(currentUser.getUserId(), t.getTeamId());
                    styleButton(followBtn, true);
                }

                // 🔥 refresh followers after click
                loadTeams();
            });

            card.getChildren().addAll(left, followBtn, trophy);
            teamContainer.getChildren().add(card);
        }
    }

    private void styleButton(Button btn, boolean isFollowing) {

        if (isFollowing) {
            btn.setText("Following");
            btn.setStyle(
                    "-fx-background-color: #00c853;" +
                            "-fx-text-fill: black;" +
                            "-fx-background-radius: 20;"
            );
        } else {
            btn.setText("Follow");
            btn.setStyle(
                    "-fx-background-color: transparent;" +
                            "-fx-border-color: #00c853;" +
                            "-fx-text-fill: #00c853;" +
                            "-fx-border-radius: 20;" +
                            "-fx-background-radius: 20;"
            );
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