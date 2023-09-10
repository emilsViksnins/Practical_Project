module com.example.expensetracking {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.expensetracking to javafx.fxml;
    exports com.example.expensetracking;
}