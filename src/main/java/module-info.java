module com.example.monopoly_1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.monopoly_1 to javafx.fxml;
    exports com.example.monopoly_1;
}