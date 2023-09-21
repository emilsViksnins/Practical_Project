package com.example.expensetracking;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginController {

    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    private Stage currentStage;

    @FXML
    private void handleLogin() {
        String username = usernameInput.getText().trim();
        String password = passwordInput.getText().trim();

        User user = ExpenseManagerApp.login(username, password);
        if (user != null) {
            ExpenseManagerApp.setCurrentUser(user);
            try {
                Stage mainAppStage = new Stage();
                AnchorPane mainAppRoot = FXMLLoader.load(getClass().getResource("ExpenseManagerApp.fxml"));
                mainAppStage.setScene(new Scene(mainAppRoot));
                mainAppStage.setTitle("Expense Manager");
                mainAppStage.show();

                if(currentStage != null) {
                    currentStage.close();
                } else {
                    System.err.println("Error: currentStage is null, cannot close the window.");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login Failed");
            alert.setHeaderText(null);
            alert.setContentText("Invalid username or password!");
            alert.showAndWait();
        }
    }
    private boolean isValidCredentials(String username, String password) {
        boolean valid = false;
        try (Connection conn = ExpenseManagerApp.connectToDB()) {
            String sql = "SELECT * FROM users WHERE username = ? AND password = ?"; // For security reasons, you should hash and salt passwords.
            PreparedStatement preparedStatement = conn.prepareStatement(sql);
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                valid = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valid;
    }
    public void setCurrentStage(Stage currentStage) {
        this.currentStage = currentStage;
    }
}
