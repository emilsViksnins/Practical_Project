package com.example.expensetracking;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.converter.IntegerStringConverter;


public class ExpenseManagerApp extends Application {
    private Connection dbConnection;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expense Tracker");

        try {
            // Establish database connection
            establishDBConnection();

            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("ExpenseManagerApp.fxml"));
            Parent root = loader.load();

            // Get the controller instance if needed
            ExpenseManagerController controller = loader.getController();

            TabPane tabPane = new TabPane();

            Tab salaryTab = new Tab("Enter your Salary");
            salaryTab.setContent(createSalaryEntryForm(controller));

            Tab expenseTab = new Tab("Manage Expenses");
            expenseTab.setContent(createExpenseManagementForm(controller));

            tabPane.getTabs().addAll(salaryTab, expenseTab);

            Scene scene = new Scene(root, 800, 600);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void establishDBConnection() {
        String dbUrl = "jdbc:mysql://localhost:3306/ExpenseManager"; // Replace with your database URL
        String dbUser = "root"; // Replace with your MySQL username
        String dbPassword = "cratislpg777"; // Replace with your MySQL password

        try {
            dbConnection = DriverManager.getConnection(dbUrl, dbUser, dbPassword);
            System.out.println("Connected to the database!");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database connection errors here
        }
    }

    private VBox createSalaryEntryForm(ExpenseManagerController controller) {
        VBox salaryEntryLayout = new VBox(10);
        Label titleLabel = new Label("Enter your Monthly Salary");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField employeeNameField = new TextField();
        employeeNameField.setPromptText("Name");
        TextField salaryAmountField = new TextField();
        salaryAmountField.setPromptText("Salary Amount");

        Button submitSalaryButton = new Button("Submit Salary");
        submitSalaryButton.setOnAction(e -> {
            // Handle the "Submit Salary" button click event
            String name = employeeNameField.getText();
            String salaryAmountText = salaryAmountField.getText();
            if (!name.isEmpty() && !salaryAmountText.isEmpty()) {
                try {
                    double salaryAmount = Double.parseDouble(salaryAmountText);
                    // Store the salary details in the database
                    storeSalaryDetails(name, salaryAmount);

                    // Show a success message in a pop-up window
                    showAlert("Success", "Your salary was successfully submitted.", Alert.AlertType.INFORMATION);

                    // Optionally, you can clear the fields or provide feedback to the user
                    employeeNameField.clear();
                    salaryAmountField.clear();
                } catch (NumberFormatException ex) {
                    // Handle invalid input for salary amount (not a valid double)
                    showAlert("Error", "Invalid salary amount. Please enter a valid number.", Alert.AlertType.ERROR);
                }
            } else {
                // Handle empty name or salary amount fields
                showAlert("Error", "Please enter both name and salary amount.", Alert.AlertType.ERROR);
            }
        });

        salaryEntryLayout.getChildren().addAll(titleLabel, employeeNameField, salaryAmountField, submitSalaryButton);

        return salaryEntryLayout;
    }

    private VBox createExpenseManagementForm(ExpenseManagerController controller) {
        VBox expenseManagementLayout = new VBox(10);
        Label titleLabel = new Label("Manage Expenses");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField expenseNameField = new TextField();
        expenseNameField.setPromptText("Expense Name");
        TextField expenseAmountField = new TextField();
        expenseAmountField.setPromptText("Expense Amount");

        // ComboBox for expense categories
        ComboBox<String> expenseCategoryComboBox = new ComboBox<>();
        expenseCategoryComboBox.setPromptText("Select Category");
        expenseCategoryComboBox.getItems().addAll("Utility Payments", "Groceries", "Entertainment", "Other");

        DatePicker expenseDateDatePicker = new DatePicker();
        expenseDateDatePicker.setPromptText("Expense Date");

        Button addExpenseButton = new Button("Add Expense");
        addExpenseButton.setOnAction(e -> {
            // Handle the "Add Expense" button click event
            String name = expenseNameField.getText();
            double amount = Double.parseDouble(expenseAmountField.getText());
            String category = expenseCategoryComboBox.getValue();
            LocalDate date = expenseDateDatePicker.getValue();

            // Store the expense details in the database
            storeExpenseDetails(name, amount, category, date);

            // Optionally, you can clear the fields or provide feedback to the user
            expenseNameField.clear();
            expenseAmountField.clear();
            expenseCategoryComboBox.getSelectionModel().clearSelection();
            expenseDateDatePicker.getEditor().clear();
        });

        expenseManagementLayout.getChildren().addAll(titleLabel, expenseNameField, expenseAmountField,
                expenseCategoryComboBox, expenseDateDatePicker, addExpenseButton);

        return expenseManagementLayout;
    }

    private void storeSalaryDetails(String name, double amount) {
        // Insert the salary details into the database
        try {
            String insertQuery = "INSERT INTO Salaries (employee_name, salary_amount) VALUES (?, ?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, amount);
            preparedStatement.executeUpdate();
            System.out.println("Salary details stored in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database insertion errors here
        }
    }

    private void storeExpenseDetails(String name, double amount, String category, LocalDate date) {
        // Insert the expense details into the database
        try {
            String insertQuery = "INSERT INTO Expenses (expense_name, expense_amount, expense_category, expense_date) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = dbConnection.prepareStatement(insertQuery);
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, amount);
            preparedStatement.setString(3, category);
            preparedStatement.setDate(4, java.sql.Date.valueOf(date));
            preparedStatement.executeUpdate();
            System.out.println("Expense details stored in the database.");
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle any database insertion errors here
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @Override
    public void stop() {
        // Close the database connection when the application is stopped
        try {
            if (dbConnection != null && !dbConnection.isClosed()) {
                dbConnection.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
