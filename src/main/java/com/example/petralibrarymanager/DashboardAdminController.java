package com.example.petralibrarymanager;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class DashboardAdminController {
        @FXML
        private VBox sidebar;

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
}
