package controller;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import model.User;

public class CreatePostController {

    @FXML
    private TextArea contentField; // 🔥 matches FXML

    @FXML
    private Label statusLabel;

    private User currentUser;

    private UserDAO dao = new UserDAO();

    public void setUser(User user) {
        this.currentUser = user;
    }

    @FXML
    private void handlePost() {

        String content = contentField.getText();

        if (content == null || content.trim().isEmpty()) {
            statusLabel.setText("Post cannot be empty");
            return;
        }

        if (currentUser == null) {
            statusLabel.setText("User not set (navigation issue)");
            return;
        }

        dao.createPost(currentUser.getUserId(), content);

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Feed.fxml"));
            Scene scene = new Scene(loader.load());

            FeedController controller = loader.getController();
            controller.setUser(currentUser);

            Stage stage = (Stage) contentField.getScene().getWindow();
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

            Stage stage = (Stage) contentField.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}