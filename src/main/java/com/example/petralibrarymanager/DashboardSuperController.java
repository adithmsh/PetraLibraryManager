package com.example.petralibrarymanager;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardSuperController {
        @FXML
        private VBox sidebar;

        @FXML private Button logoutButton;

        @FXML
        private AnchorPane mainContent;

        @FXML
        public void initialize() {
            // Called automatically after FXML is loaded
            System.out.println("Dashboard initialized!");

            // Example: hide sidebar on startup
            // sidebar.setVisible(false);
            // sidebar.setManaged(false);
        }

        // Example method to toggle sidebar visibility
        public void toggleSidebar() {
            boolean currentlyVisible = sidebar.isVisible();
            sidebar.setVisible(!currentlyVisible);
            sidebar.setManaged(!currentlyVisible);
        }

        // You can also load new content into mainContent
        // public void loadPage(Node node) {
        //     mainContent.getChildren().setAll(node);
        // }

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

}
