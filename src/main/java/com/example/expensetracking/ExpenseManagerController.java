package com.example.expensetracking;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class ExpenseManagerController {

    @FXML
    private TextField salaryInput;

    @FXML
    private TextField expenseInput;

    @FXML
    private TextField commentInput;

    @FXML
    private Label resultLabel;
    @FXML
    private ImageView userIcon;

    @FXML
    public void handleSubmit() {
        float salary = Float.parseFloat(salaryInput.getText().isEmpty() ? "0" : salaryInput.getText());
        float expense = Float.parseFloat(expenseInput.getText().isEmpty() ? "0" : expenseInput.getText());
        String comment = commentInput.getText();

        if (salary > 0 && ExpenseManagerApp.hasSalaryForCurrentMonth()) {
            resultLabel.setText("You've already entered salary this month. Enter only expense and comment.");
            return;
        }

        saveTransaction(salary, expense, comment);

        float remaining = ExpenseManagerApp.getRemainingSalary();
        resultLabel.setText("Remaining Salary: " + remaining);
    }
    private void saveTransaction(float salary, float expense, String comment) {
        try (Connection conn = ExpenseManagerApp.connectToDB();
             PreparedStatement pstmt = conn.prepareStatement("INSERT INTO transactions(user_id, date, salary, expense, comment) VALUES(?, CURDATE(), ?, ?, ?)")) {
            pstmt.setInt(1, ExpenseManagerApp.getCurrentUser().getId());
            pstmt.setFloat(2, salary);
            pstmt.setFloat(3, expense);
            pstmt.setString(4, comment);
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
