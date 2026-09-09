package controller;

import dao.UserDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.User;

public class SignupController {

    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private PasswordField passwordField;
    @FXML private Label statusLabel;

    private UserDAO dao = new UserDAO();

    @FXML
    private void handleSignup() {

        String username = usernameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        // 🔒 validation
        if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please fill all fields");
            return;
        }

        try {
            // ✅ USE CONSTRUCTOR (NO DEFAULT CONSTRUCTOR NEEDED)
            User user = new User(
                    0,              // userId not needed (DB uses sequence)
                    username,
                    email,
                    password,
                    "USER"
            );

            dao.registerUser(user);

            statusLabel.setText("User created successfully!");

            usernameField.clear();
            emailField.clear();
            passwordField.clear();

        } catch (Exception e) {
            statusLabel.setText("Error creating user");
            e.printStackTrace();
        }
    }

    @FXML
    private void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}