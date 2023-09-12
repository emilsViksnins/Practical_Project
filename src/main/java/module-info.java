module com.example.expensetracking {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.expensetracking to javafx.fxml;
    exports com.example.expensetracking;
}