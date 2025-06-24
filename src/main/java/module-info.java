module com.example.petralibrarymanager {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.petralibrarymanager to javafx.fxml;
    exports com.example.petralibrarymanager;
    exports com.example.petralibrarymanager.database;
    opens com.example.petralibrarymanager.database to javafx.fxml;

    exports com.example.petralibrarymanager.contents to javafx.fxml;
    opens com.example.petralibrarymanager.contents to javafx.fxml;

    exports com.example.petralibrarymanager.database.models;
    opens com.example.petralibrarymanager.database.models to javafx.base;

}