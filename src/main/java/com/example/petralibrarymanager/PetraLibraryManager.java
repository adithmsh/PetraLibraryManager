package com.example.petralibrarymanager;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class PetraLibraryManager extends Application {
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

        /* MOTHER FUCKER, I CAN'T FIXED THIS... PLEASE HELP! */
        Image icon = new Image(getClass().getResourceAsStream("icon.jpeg"));

        stage.setTitle("Petra Library Manager");
        stage.getIcons().add(icon); // Set the


        stage.sizeToScene();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
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