package com.example.expensetracking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;

public class ExpenseManagerApp extends Application {

        private static final String DB_URL = "jdbc:mysql://localhost:3306/ExpenseManager";
        private static final String USER = "root";
        private static final String PASS = "cratislpg777";

        public static void main(String[] args) {
                launch(args);
        }

        @Override
        public void start(Stage primaryStage) throws Exception {
                VBox root = FXMLLoader.load(getClass().getResource("ExpenseManagerApp.fxml"));
                Scene scene = new Scene(root);
                primaryStage.setTitle("Expense Manager");
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


        public static float getRemainingSalary() {
                try (Connection conn = connectToDB();
                     Statement stmt = conn.createStatement()) {
                        String sql = "SELECT (SUM(salary) - SUM(expense)) as balance FROM transactions";
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                                return rs.getFloat("balance");
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return 0.0f;

        }
        public static boolean hasSalaryForCurrentMonth() {
                try (Connection conn = connectToDB();
                     Statement stmt = conn.createStatement()) {
                        String sql = "SELECT COUNT(*) as count FROM transactions WHERE MONTH(date) = MONTH(CURDATE()) AND YEAR(date) = YEAR(CURDATE()) AND salary > 0";
                        ResultSet rs = stmt.executeQuery(sql);
                        if (rs.next()) {
                                return rs.getInt("count") > 0;
                        }
                } catch (Exception e) {
                        e.printStackTrace();
                }
                return false;
        }
}
