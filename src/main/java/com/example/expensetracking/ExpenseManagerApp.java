package com.example.expensetracking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ExpenseManagerApp extends Application {
        private static final String DB_URL = "jdbc:mysql://localhost:3306/expense_manager";
        private static final String USER = "root";
        private static final String PASS = "cratislpg777";
        private static User currentUser;
        public static void main(String[] args) {
                launch(args);
        }
        @Override
        public void start(Stage primaryStage) throws Exception {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Login.fxml"));
                AnchorPane root = (AnchorPane) loader.load();
                LoginController controller = loader.getController();
                controller.setCurrentStage(primaryStage);
                Scene scene = new Scene(root);
                primaryStage.setTitle("Expense Manager - Login");
                primaryStage.setScene(scene);
                primaryStage.show();
        }
        public static Connection connectToDB() {
                try {
                        return DriverManager.getConnection(DB_URL, USER, PASS);
                } catch (Exception e) {
                        e.printStackTrace();
                        return null;
                }
        }
        public static User login(String username, String password) {
                try (Connection conn = connectToDB();
                     PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?")) {
                        stmt.setString(1, username);
                        stmt.setString(2, password);
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                                return new User(rs.getInt("user_id"), rs.getString("username"));
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return null;
        }
        public static float getRemainingSalary() {
                if (currentUser == null) {
                        System.err.println("Error: currentUser is null. Make sure to set a valid user before calling this method.");
                        return 0.0f;
                }
                try (Connection conn = connectToDB();
                     PreparedStatement stmt = conn.prepareStatement("SELECT (SUM(salary) - SUM(expense)) as balance FROM transactions WHERE user_id = ?")) {
                        stmt.setInt(1, currentUser.getId());
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                                return rs.getFloat("balance");
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return 0.0f;
        }
        public static boolean hasSalaryForCurrentMonth() {
                if (currentUser == null) {
                        System.err.println("Error: currentUser is null. Make sure to set a valid user before calling this method.");
                        return false;
                }
                try (Connection conn = connectToDB();
                     PreparedStatement stmt = conn.prepareStatement("SELECT COUNT(*) as count FROM transactions WHERE user_id = ? AND MONTH(date) = MONTH(CURDATE()) AND YEAR(date) = YEAR(CURDATE()) AND salary > 0")) {
                        stmt.setInt(1, currentUser.getId());
                        ResultSet rs = stmt.executeQuery();
                        if (rs.next()) {
                                return rs.getInt("count") > 0;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return false;
        }
        public static User getCurrentUser() {
                return currentUser;
        }
        public static void setCurrentUser(User user) {
                currentUser = user;
        }
}
