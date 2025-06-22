package com.example.petralibrarymanager;

import com.example.petralibrarymanager.graphical.BackgroundFader;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class DashboardSuperController {
        @FXML
        private VBox sidebar;

        @FXML private Button logoutButton;
        @FXML private StackPane rootPane;

        @FXML
        private AnchorPane mainContent;

        private List<Image> backgrounds = List.of(
                new Image(getClass().getResource("images/bgs/5.jpg").toExternalForm()),
                new Image(getClass().getResource("images/bgs/2.jpg").toExternalForm()),
                new Image(getClass().getResource("images/bgs/3.jpg").toExternalForm()),
                new Image(getClass().getResource("images/bgs/4.jpg").toExternalForm())
        );

        @FXML
        public void initialize() {
            // Called automatically after FXML is loaded
            System.out.println("Dashboard initialized!");
//            bgImage1.setVisible(false);
//            BackgroundFader.setupCrossfadeBackground(rootPane, backgrounds, 5);

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
