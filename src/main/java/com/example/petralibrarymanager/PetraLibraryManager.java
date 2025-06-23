package com.example.petralibrarymanager;

import com.example.petralibrarymanager.database.DataBaseManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;


public class PetraLibraryManager extends Application {

    static double width, height;
    @Override
    public void start(Stage stage) throws IOException {

        /* @adith:
            since the dashboard view of the app depends on the type of user.
            we need to have two separate dashboard views.

            Update: We will distinguish between three types of users(roles):
             - Member,  : Good:>
             - Staff,
             - Admin    : extends the abilities of the staff, but includes the ability manage users and global policies.

            Therefore, there will be two dashboards. Member and Non-Member. Since the staff role is pretty similar to the admin role.
         */

        FXMLLoader fxmlLoader = new FXMLLoader(PetraLibraryManager.class.getResource("login-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
//        scene.getStylesheets().add(getClass().getResource("styles/style.css").toExternalForm());
        /* MOTHER FUCKER, I CAN'T FIXED THIS... PLEASE HELP! */
        Image icon = new Image(getClass().getResourceAsStream("icon.jpeg"));

        stage.setTitle("Petra Library Manager");
        stage.getIcons().add(icon); // Set the


        // Get the primary screen bounds
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();

// Calculate percentage-based width and height
        width = screenBounds.getWidth() * 0.7;   // 80% of screen width
        height = screenBounds.getHeight() * 0.8; // 80% of screen height

// Set stage size
        stage.setWidth(width);
        stage.setHeight(height);

// Optional: center the stage on the screen
        stage.setX((screenBounds.getWidth() - width) / 2);
        stage.setY((screenBounds.getHeight() - height) / 2);


        stage.sizeToScene();
        stage.setResizable(false);


        stage.setScene(scene);
        stage.show();

        Platform.runLater(() -> {
            double preferredWidth = stage.getScene().getRoot().prefWidth(-1);
            stage.setWidth(preferredWidth + 16); // add window decoration width
        });
    }

    public static void main(String[] args) {
        DataBaseManager.connect("postgres","adith123");
        launch();
    }

    @Override
    public void stop() throws Exception {
        System.out.println("App is closing...");
        if(DataBaseManager.close()) { // This will close the connection
            super.stop();
            System.out.println("application terminated.");
        }
        else System.out.println("The application cannot be closed due to failure in closing the connection to the database.");
    }
}