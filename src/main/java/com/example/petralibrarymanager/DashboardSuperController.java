package com.example.petralibrarymanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardSuperController {
        @FXML
        private VBox sidebar;

        @FXML private Button homeButton;
        @FXML private Button catalogButton;
        @FXML private Button circulationsButton;
        @FXML private Button reservationsButton;
        @FXML private Button finesButton;
        @FXML private Button settingsButton;
        @FXML private Button logoutButton;
        @FXML private StackPane rootPane;

        @FXML
        private AnchorPane mainContent;

        @FXML
        public void initialize() {
            // Called automatically after FXML is loaded
            try {
                // Load new FXML
                Node newView = FXMLLoader.load(getClass().getResource("home-super-subview.fxml"));

                // Replace content
                mainContent.getChildren().setAll(newView);

                // Optional: anchor the new content
                AnchorPane.setTopAnchor(newView, 0.0);
                AnchorPane.setRightAnchor(newView, 0.0);
                AnchorPane.setBottomAnchor(newView, 0.0);
                AnchorPane.setLeftAnchor(newView, 0.0);
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Dashboard initialized!");
        }

        // Example method to toggle sidebar visibility
        public void toggleSidebar() {
            boolean currentlyVisible = sidebar.isVisible();
            sidebar.setVisible(!currentlyVisible);
            sidebar.setManaged(!currentlyVisible);
        }


    @FXML protected void onLogoutClicked() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("login-view.fxml"));
        try {
            // Load the new FXML
            Parent loginRoot = loader.load();

            // Get the stage from the button
            Stage stage = (Stage) logoutButton.getScene().getWindow();

            // Set the new scene
            Scene loginScene = new Scene(loginRoot);
            stage.setScene(loginScene);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML protected void onSettingClicked() {
        System.out.println("Setting  button is clicked.");
        try {
            // Load new FXML
            Node newView = FXMLLoader.load(getClass().getResource("settings-super-subview.fxml"));

            // Replace content
            mainContent.getChildren().setAll(newView);

            // Optional: anchor the new content
            AnchorPane.setTopAnchor(newView, 0.0);
            AnchorPane.setRightAnchor(newView, 0.0);
            AnchorPane.setBottomAnchor(newView, 0.0);
            AnchorPane.setLeftAnchor(newView, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void onHomeClicked() {
        try {
            // Load new FXML
            Node newView = FXMLLoader.load(getClass().getResource("home-super-subview.fxml"));

            // Replace content
            mainContent.getChildren().setAll(newView);

            // Optional: anchor the new content
            AnchorPane.setTopAnchor(newView, 0.0);
            AnchorPane.setRightAnchor(newView, 0.0);
            AnchorPane.setBottomAnchor(newView, 0.0);
            AnchorPane.setLeftAnchor(newView, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void onCatalogClicked() {
        try {
            // Load new FXML
            Node newView = FXMLLoader.load(getClass().getResource("catalog-super-subview.fxml"));

            // Replace content
            mainContent.getChildren().setAll(newView);

            // Optional: anchor the new content
            AnchorPane.setTopAnchor(newView, 0.0);
            AnchorPane.setRightAnchor(newView, 0.0);
            AnchorPane.setBottomAnchor(newView, 0.0);
            AnchorPane.setLeftAnchor(newView, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void onReservationsClicked() {
        try {
            // Load new FXML
            Node newView = FXMLLoader.load(getClass().getResource("reservations-super-subview.fxml"));

            // Replace content
            mainContent.getChildren().setAll(newView);

            // Optional: anchor the new content
            AnchorPane.setTopAnchor(newView, 0.0);
            AnchorPane.setRightAnchor(newView, 0.0);
            AnchorPane.setBottomAnchor(newView, 0.0);
            AnchorPane.setLeftAnchor(newView, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void onFinesClicked() {
        try {
            // Load new FXML
            Node newView = FXMLLoader.load(getClass().getResource("fines-super-subview.fxml"));

            // Replace content
            mainContent.getChildren().setAll(newView);

            // Optional: anchor the new content
            AnchorPane.setTopAnchor(newView, 0.0);
            AnchorPane.setRightAnchor(newView, 0.0);
            AnchorPane.setBottomAnchor(newView, 0.0);
            AnchorPane.setLeftAnchor(newView, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML protected void onCirculationsClicked() {
        try {
            // Load new FXML
            Node newView = FXMLLoader.load(getClass().getResource("circulations-super-subview.fxml"));

            // Replace content
            mainContent.getChildren().setAll(newView);

            // Optional: anchor the new content
            AnchorPane.setTopAnchor(newView, 0.0);
            AnchorPane.setRightAnchor(newView, 0.0);
            AnchorPane.setBottomAnchor(newView, 0.0);
            AnchorPane.setLeftAnchor(newView, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
