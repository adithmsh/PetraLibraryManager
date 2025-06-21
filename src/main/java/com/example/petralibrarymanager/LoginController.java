package com.example.petralibrarymanager;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

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
                    System.out.println("user is found!");
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