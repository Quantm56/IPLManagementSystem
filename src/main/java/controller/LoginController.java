package controller;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

public class LoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private UserDAO dao = new UserDAO();

    @FXML
    private void handleLogin() {

        String username = usernameField.getText();
        String password = passwordField.getText();

        User user = dao.loginUser(username, password);

        if (user == null) {
            statusLabel.setText("Invalid login");
            return;
        }

        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();

            if (user.getRole().equalsIgnoreCase("ADMIN")) {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/AdminDashboard.fxml"));
                Scene scene = new Scene(loader.load());

                AdminDashboardController controller = loader.getController();
                controller.setUser(user);

                stage.setScene(scene);

            } else {

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Feed.fxml"));
                Scene scene = new Scene(loader.load());

                FeedController controller = loader.getController();
                controller.setUser(user);

                stage.setScene(scene);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void openSignup() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/Signup.fxml"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}