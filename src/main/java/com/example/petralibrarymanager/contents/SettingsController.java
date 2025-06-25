package com.example.petralibrarymanager.contents;

import com.example.petralibrarymanager.database.DataBaseManager;
import com.example.petralibrarymanager.database.models.User;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.time.LocalDateTime;

public class SettingsController {
    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;
    @FXML private Label privilegeLabel;
    @FXML private Label createdAtLabel;

    @FXML private Button changePasswordButton;
    @FXML private Button manageUsersButton;
    @FXML private Button manageBorrowingPoliciesButton;

    @FXML public void initialize() {
        usernameLabel.setText(": " + DataBaseManager.username);
        emailLabel.setText(": " + DataBaseManager.email);
        privilegeLabel.setText(": " + DataBaseManager.role);
        createdAtLabel.setText(": " + DataBaseManager.createdAt);
    }


    @FXML protected void changePasswordButtonClicked() {
        System.out.println("change password button clicked.");
        Stage currentStage = (Stage) changePasswordButton.getScene().getWindow();
        PasswordChangeDialog.show(currentStage);
    }

    @FXML protected void changeBorrowingPoliciesButtonClicked() {
        System.out.println("change borrowing policies button clicked. ");
        Stage currentStage = (Stage) manageBorrowingPoliciesButton.getScene().getWindow();
        BorrowingPolicyDialog.show(currentStage);
    }

    @FXML protected void manageUsersButtonClicked() {
        ManageUsersDialog.show((Stage) manageUsersButton.getScene().getWindow());
    }

    class PasswordChangeDialog {
        public static void show(Stage ownerStage) {
            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(ownerStage);
            dialog.setTitle("Change Password");

            PasswordField oldPassword = new PasswordField();
            PasswordField newPassword = new PasswordField();
            PasswordField confirmPassword = new PasswordField();

            Button submitButton = new Button("Change Password");

            submitButton.setOnAction(e -> {
                // Handle password validation and update logic
                dialog.close();
            });

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(20));
            grid.setVgap(10);
            grid.setHgap(10);

            grid.add(new Label("Old Password:"), 0, 0);
            grid.add(oldPassword, 1, 0);
            grid.add(new Label("New Password:"), 0, 1);
            grid.add(newPassword, 1, 1);
            grid.add(new Label("Confirm Password:"), 0, 2);
            grid.add(confirmPassword, 1, 2);
            grid.add(submitButton, 1, 3);

            dialog.setScene(new Scene(grid));
            dialog.showAndWait();
        }
    }


    public class BorrowingPolicyDialog {
        public static void show(Stage ownerStage) {
            Stage dialog = new Stage();
            dialog.initModality(Modality.WINDOW_MODAL);
            dialog.initOwner(ownerStage);
            dialog.setTitle("Borrowing Policies");

            Spinner<Integer> maxBooksSpinner = new Spinner<>(1, 20, 5);
            Spinner<Integer> durationSpinner = new Spinner<>(1, 60, 14);
            Spinner<Double> fineRateSpinner = new Spinner<>(0.0, 50.0, 1.0, 0.5);
            fineRateSpinner.setEditable(true);

            Button saveButton = new Button("Save Policies");

            saveButton.setOnAction(e -> {
                int maxBooks = maxBooksSpinner.getValue();
                int borrowDays = durationSpinner.getValue();
                double fineRate = fineRateSpinner.getValue();

                // Store or update your settings here
                dialog.close();
            });

            GridPane grid = new GridPane();
            grid.setPadding(new Insets(20));
            grid.setVgap(10);
            grid.setHgap(10);

            grid.add(new Label("Max Books:"), 0, 0);
            grid.add(maxBooksSpinner, 1, 0);
            grid.add(new Label("Borrow Duration (days):"), 0, 1);
            grid.add(durationSpinner, 1, 1);
            grid.add(new Label("Late Fine Rate (/day):"), 0, 2);
            grid.add(fineRateSpinner, 1, 2);
            grid.add(saveButton, 1, 3);

            dialog.setScene(new Scene(grid));
            dialog.showAndWait();
        }
    }

    class ManageUsersDialog {

        public static void show(Stage ownerStage) {
            Stage dialog = new Stage();
            dialog.setTitle("Manage Users");
            dialog.initModality(Modality.APPLICATION_MODAL);
            dialog.initOwner(ownerStage);

            TableView<User> tableView = new TableView<>();
            ObservableList<User> users = FXCollections.observableArrayList(
                    new User("Alice", "alice@example.com", "password1", "admin", "2024-01-10"),
                    new User("Bob", "bob@example.com", "password2", "staff", "2024-02-05")
            );

            TableColumn<User, String> nameCol = new TableColumn<>("Name");
            nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));

            TableColumn<User, String> emailCol = new TableColumn<>("Email");
            emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

            TableColumn<User, String> passCol = new TableColumn<>("Password");
            passCol.setCellValueFactory(new PropertyValueFactory<>("password"));

            TableColumn<User, String> roleCol = new TableColumn<>("Role");
            roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));

            TableColumn<User, String> createdCol = new TableColumn<>("Created At");
            createdCol.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

            tableView.setItems(users);
            tableView.getColumns().addAll(nameCol, emailCol, roleCol, passCol, createdCol);
            tableView.setPrefHeight(250);
            tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

            TextField nameField = new TextField();
            nameField.setPromptText("Name");
            TextField emailField = new TextField();
            emailField.setPromptText("Email");
            TextField roleField = new TextField();
            roleField.setPromptText("Role");
            PasswordField passwordField = new PasswordField();
            passwordField.setPromptText("Password");


            Button addButton = new Button("Add");
            Button deleteButton = new Button("Delete");
            Button updateButton = new Button("Update");

            addButton.setOnAction(e -> {
                String createdAt = LocalDateTime.now().toLocalDate().toString();
                System.out.println(roleField.getText());
                User user = new User(
                        nameField.getText(),
                        emailField.getText(),
                        passwordField.getText(),
                        roleField.getText(),
                        createdAt
                );
                users.add(user);
                nameField.clear();
                emailField.clear();
                roleField.clear();
                passwordField.clear();
            });

            deleteButton.setOnAction(e -> {
                User selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    users.remove(selected);
                }
            });

            updateButton.setOnAction(e -> {
                User selected = tableView.getSelectionModel().getSelectedItem();
                if (selected != null) {
                    selected.setName(nameField.getText());
                    selected.setEmail(emailField.getText());
                    selected.setRole(roleField.getText());
                    selected.setPassword(passwordField.getText());
                    tableView.refresh();
                }
            });

            HBox buttons = new HBox(10, addButton, updateButton, deleteButton);
            GridPane inputForm = new GridPane();
            inputForm.setPadding(new Insets(10));
            inputForm.setHgap(10);
            inputForm.setVgap(10);

            inputForm.add(new Label("Name:"), 0, 0);
            inputForm.add(nameField, 1, 0);
            inputForm.add(new Label("Email:"), 0, 1);
            inputForm.add(emailField, 1, 1);
            inputForm.add(new Label("Role:"), 0, 2);
            inputForm.add(roleField, 1, 2);
            inputForm.add(new Label("Password:"), 0, 3);
            inputForm.add(passwordField, 1, 3);

            VBox layout = new VBox(10, tableView, inputForm, buttons);
            layout.setPadding(new Insets(15));

            Scene scene = new Scene(layout);
            scene.getStylesheets().add("/styles/style.css");

            dialog.setScene(scene);
            dialog.showAndWait();
        }
    }

}
