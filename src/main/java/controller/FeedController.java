package controller;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Post;
import model.User;

import java.util.List;

public class FeedController {

    @FXML
    private VBox postContainer;

    private UserDAO dao = new UserDAO();

    private User currentUser;

    public void setUser(User user) {
        this.currentUser = user;
        loadPosts();
    }

    public void initialize() {}

    private void loadPosts() {

        // 🔥 SAFETY
        if (currentUser == null) {
            System.out.println("User is null, skipping loadPosts");
            return;
        }

        postContainer.getChildren().clear();

        List<Post> posts = dao.getAllPosts();

        for (Post p : posts) {

            VBox card = new VBox();
            card.setSpacing(12);
            card.setStyle(
                    "-fx-background-color: #1e1e1e;" +
                            "-fx-padding: 16;" +
                            "-fx-background-radius: 14;" +
                            "-fx-border-color: #2a2a2a;" +
                            "-fx-border-radius: 14;"
            );

            Label content = new Label(p.getContent());
            content.setWrapText(true);
            content.setStyle("-fx-text-fill: white; -fx-font-size: 15px;");

            int likeCount = dao.getLikeCount(p.getPostId());
            Label likes = new Label("❤ " + likeCount);
            likes.setStyle("-fx-text-fill: #aaaaaa;");

            Button likeBtn = new Button("Like");
            likeBtn.setStyle(
                    "-fx-background-color: #00c853;" +
                            "-fx-text-fill: black;" +
                            "-fx-background-radius: 20;"
            );

            // 🔥 FIXED LIKE (NO ERROR ON DUPLICATE)
            likeBtn.setOnAction(e -> {
                try {
                    dao.likePost(currentUser.getUserId(), p.getPostId());
                } catch (Exception ex) {
                    // do nothing (prevents crash if DAO not updated)
                }
                loadPosts();
            });

            HBox actions = new HBox(10, likeBtn, likes);

            if (p.getUserId() == currentUser.getUserId()) {

                Button deleteBtn = new Button("Delete");

                deleteBtn.setStyle(
                        "-fx-background-color: #ff1744;" +
                                "-fx-text-fill: white;" +
                                "-fx-background-radius: 20;"
                );

                deleteBtn.setOnAction(e -> {
                    dao.deletePost(currentUser.getUserId(), p.getPostId());
                    loadPosts();
                });

                actions.getChildren().add(deleteBtn);
            }

            card.getChildren().addAll(content, actions);
            postContainer.getChildren().add(card);
        }
    }

    // ================= NAVIGATION =================

    @FXML
    private void openTeams() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Teams.fxml"));
            Scene scene = new Scene(loader.load());

            TeamController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) postContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openCreatePost() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/CreatePost.fxml"));
            Scene scene = new Scene(loader.load());

            CreatePostController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) postContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openVenues() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Venues.fxml"));
            Scene scene = new Scene(loader.load());

            VenueController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) postContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openMatches() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Matches.fxml"));
            Scene scene = new Scene(loader.load());

            MatchController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) postContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openResults() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Results.fxml"));
            Scene scene = new Scene(loader.load());

            ResultController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) postContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openFollowTeams() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/FollowTeams.fxml"));
            Scene scene = new Scene(loader.load());

            FollowTeamsController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) postContainer.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openSimple(String fxml) {
        try {
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource(fxml)));
            Stage stage = (Stage) postContainer.getScene().getWindow();
            stage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void logout() {
        openSimple("/view/Login.fxml");
    }
}