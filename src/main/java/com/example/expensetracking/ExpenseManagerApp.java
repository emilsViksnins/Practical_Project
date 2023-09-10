package com.example.expensetracking;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class ExpenseManagerApp extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expense Tracker");

        TabPane tabPane = new TabPane();

        Tab salaryTab = new Tab("Enter your Salary");
        salaryTab.setContent(createSalaryEntryForm());

        Tab expenseTab = new Tab("Manage Expenses");
        expenseTab.setContent(createExpenseManagementForm());

        tabPane.getTabs().addAll(salaryTab, expenseTab);

        Scene scene = new Scene(tabPane, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private VBox createSalaryEntryForm() {
        VBox salaryEntryLayout = new VBox(10);
        Label titleLabel = new Label("Enter your Monthly Salary");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField employeeNameField = new TextField();
        employeeNameField.setPromptText("Name");
        TextField salaryAmountField = new TextField();
        salaryAmountField.setPromptText("Salary Amount");

        Button submitSalaryButton = new Button("Submit Salary");

        salaryEntryLayout.getChildren().addAll(titleLabel, employeeNameField, salaryAmountField, submitSalaryButton);

        return salaryEntryLayout;
    }
    private VBox createExpenseManagementForm() {
        VBox expenseManagementLayout = new VBox(10);
        Label titleLabel = new Label("Manage Expenses");
        titleLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");

        TextField expenseNameField = new TextField();
        expenseNameField.setPromptText("Expense Name");
        TextField expenseAmountField = new TextField();
        expenseAmountField.setPromptText("Expense Amount");

        Button addExpenseButton = new Button("Add Expense");

        expenseManagementLayout.getChildren().addAll(titleLabel, expenseNameField, expenseAmountField, addExpenseButton);

        return expenseManagementLayout;
    }
}
