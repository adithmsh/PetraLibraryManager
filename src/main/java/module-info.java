module com.example.petralibrarymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.petralibrarymanager to javafx.fxml;
    exports com.example.petralibrarymanager;
}