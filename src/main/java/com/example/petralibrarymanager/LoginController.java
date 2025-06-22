package com.example.petralibrarymanager;

import com.example.petralibrarymanager.database.DataBaseManager;
import com.example.petralibrarymanager.graphical.BackgroundFader;
import javafx.animation.Animation;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.util.Duration;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class LoginController {

    @FXML private Button loginButton;
    @FXML private TextField identifierField;
    @FXML private PasswordField passwordField;
    @FXML private Label messageLabel;
    @FXML private VBox loginVBox;
//    @FXML private ImageView bgImage2;
    @FXML private ImageView bgImage1;
    @FXML private StackPane rootPane;

    private List<Image> backgrounds = List.of(
            new Image(getClass().getResource("images/bgs/5.jpg").toExternalForm()),
            new Image(getClass().getResource("images/bgs/2.jpg").toExternalForm()),
            new Image(getClass().getResource("images/bgs/3.jpg").toExternalForm()),
            new Image(getClass().getResource("images/bgs/4.jpg").toExternalForm())
    );

//    private int index = 0;

    @FXML
    public void initialize() {
        // Apply blur
//        BoxBlur blur = new BoxBlur();
//        blur.setWidth(5);
//        blur.setHeight(5);
//        blur.setIterations(3);
//
//        loginVBox.setEffect(blur);


//        bgImage2.setImage(backgrounds.get(index));
//        startImageRotation();

//        bgImage2.fitWidthProperty().bind(rootPane.widthProperty());
//        bgImage2.fitHeightProperty().bind(rootPane.heightProperty());
        bgImage1.fitWidthProperty().bind(rootPane.widthProperty());
        bgImage1.fitHeightProperty().bind(rootPane.heightProperty());
//        BackgroundFader.setupCrossfadeBackground(rootPane, backgrounds, 5);
        messageLabel.setVisible(false);
    }

//    private void startImageRotation() {
//        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(8), e -> {
//            index = (index + 1) % backgrounds.size();
//            bgImage2.setImage(backgrounds.get(index));
//        }));
//        timeline.setCycleCount(Animation.INDEFINITE);
//        timeline.play();
//    }

    @FXML
    protected void onLoginClicked() {
        System.out.println(identifierField.getText());
        System.out.println(passwordField.getText());
        System.out.println("Hello, World");

        String identifier, password_hash;
        identifier = identifierField.getText(); password_hash = passwordField.getText();
        if(identifier.isEmpty() || password_hash.isEmpty()) {
            messageLabel.setVisible(true);
            messageLabel.setText("Please fill in the fields properly!");
        } else {
            try {

                String st = null;
                /* if the identifier is an email, then we have to check using the email. Otherwise, use username.*/
                if(identifier.contains("@")) {
                    st = "SELECT * FROM users WHERE email = ? AND password_hash = ?;";
                } else {
                    st = "SELECT * FROM users WHERE name = ? AND password_hash = ?;";
                }

                PreparedStatement qst = DataBaseManager.conn.prepareStatement(st);
                qst.setString(1, identifier);
                qst.setString(2, password_hash);
                ResultSet rset = qst.executeQuery();

                if(rset.next()) {
                    DataBaseManager.identifier = identifier;
                    DataBaseManager.password = password_hash;
                    DataBaseManager.role = rset.getString("role");
                    System.out.println("user is found! " + "privilege: " + DataBaseManager.role);




                    FXMLLoader loader = null;
                    if(DataBaseManager.role.equalsIgnoreCase("admin")) {
                        loader = new FXMLLoader(getClass().getResource("dashboard-super-view.fxml"));
                    } else {
                        loader = new FXMLLoader(getClass().getResource("dashboard-super-view.fxml"));
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
                    messageLabel.setText("The username/email or password is not found. Please try again!");
                }

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }



    }
}