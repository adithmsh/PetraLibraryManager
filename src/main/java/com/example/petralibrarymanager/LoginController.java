package com.example.petralibrarymanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginController {

    @FXML private Button loginButton;
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;

    @FXML
    public void initialize() {
        messageLabel.setVisible(false);
    }

    @FXML
    protected void onLoginClicked() {
        System.out.println(usernameField.getText());
        System.out.println(passwordField.getText());
        System.out.println("Hello, World");

        String username, password;
        username = usernameField.getText(); password = passwordField.getText();
        if(username.isEmpty() || password.isEmpty()) {
            messageLabel.setVisible(true);
            messageLabel.setText("Please fill in the fields properly!");
        } else {
            try {
                String st = "SELECT * FROM users WHERE username = ? AND password = ?;";
                PreparedStatement qst = DataBaseManager.conn.prepareStatement(st);
                qst.setString(1, username);
                qst.setString(2, password);

                ResultSet rset = qst.executeQuery();

                if(rset.next()) {
                    DataBaseManager.username = username;
                    DataBaseManager.password = password;
                    DataBaseManager.privilege = rset.getString("privilege");
                    System.out.println("user is found! " + "privilege: " + DataBaseManager.privilege);




                    FXMLLoader loader = null;
                    if(DataBaseManager.privilege.equalsIgnoreCase("admin")) {
                        loader = new FXMLLoader(getClass().getResource("dashboard-admin-view.fxml"));
                    } else {
                        loader = new FXMLLoader(getClass().getResource("dashboard-admin-view.fxml"));
                    }

                    try {
                        // Load the new FXML
                        Parent dashboardRoot = loader.load();

                        // Get the stage from the button
                        Stage stage = (Stage) loginButton.getScene().getWindow();

                        // Set the new scene
                        Scene dashboardScene = new Scene(dashboardRoot);
                        stage.setScene(dashboardScene);
//                        stage.setTitle("Petra LibraryDashboard");

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }



                } else {
                    messageLabel.setVisible(true);
                    messageLabel.setText("The username or password is not found. Please try again!");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



    }
}