package com.example.petralibrarymanager.contents;


import com.example.petralibrarymanager.database.models.Fine;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

import javafx.scene.control.TableCell;
import javafx.util.Callback;

import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.beans.binding.Bindings;
import javafx.scene.control.ChoiceDialog;

public class FinesController {

    @FXML private TableView<Fine> finesTable;
    @FXML private TableColumn<Fine, String> borrowerColumn;
    @FXML private TableColumn<Fine, String> bookColumn;
    @FXML private TableColumn<Fine, Double> amountColumn;
    @FXML private TableColumn<Fine, LocalDate> dueDateColumn;
    @FXML private TableColumn<Fine, String> statusColumn;

    private final ObservableList<Fine> dummyFines = FXCollections.observableArrayList(
            new Fine("Alice", "The Hobbit", 15000, LocalDate.now().minusDays(3), "Unpaid"),
            new Fine("Bob", "Effective Java", 20000, LocalDate.now().minusDays(10), "Unpaid"),
            new Fine("Charlie", "Clean Code", 10000, LocalDate.now().minusDays(5), "Paid"),
            new Fine("Diana", "JavaFX Mastery", 12000, LocalDate.now().minusDays(1), "Unpaid"),
            new Fine("Ethan", "The Alchemist", 8000, LocalDate.now().minusDays(7), "Paid"),
            new Fine("Fiona", "Data Structures", 17000, LocalDate.now().minusDays(2), "Unpaid"),
            new Fine("George", "Design Patterns", 14000, LocalDate.now().minusDays(6), "Paid"),
            new Fine("Hannah", "UI/UX Design", 11000, LocalDate.now().minusDays(8), "Unpaid"),
            new Fine("Ian", "Deep Learning", 9000, LocalDate.now().minusDays(4), "Paid"),
            new Fine("Jenna", "Database Systems", 13000, LocalDate.now().minusDays(9), "Unpaid")
    );

    @FXML
    public void initialize() {
        borrowerColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        bookColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("amount"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        finesTable.setItems(dummyFines);
        finesTable.setRowFactory(tv -> {
            TableRow<Fine> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            // Change Status menu item (conditionally shown)
            MenuItem changeStatusItem = new MenuItem("Mark as Paid");

            changeStatusItem.setOnAction(event -> {
                Fine selectedFine = row.getItem();
                if (selectedFine != null && "unpaid".equalsIgnoreCase(selectedFine.getStatus())) {
                    selectedFine.setStatus("paid");
                    finesTable.refresh(); // Update table view
                }
            });

            // Delete option
            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(event -> {
                Fine selectedFine = row.getItem();
                if (selectedFine != null) {
                    finesTable.getItems().remove(selectedFine);
                }
            });

            // Build menu conditionally
            row.itemProperty().addListener((obs, oldItem, newItem) -> {
                contextMenu.getItems().clear();
                if (newItem != null) {
                    if ("unpaid".equalsIgnoreCase(newItem.getStatus())) {
                        contextMenu.getItems().add(changeStatusItem);
                    }
                    contextMenu.getItems().add(deleteItem);
                }
            });

            row.setContextMenu(contextMenu);
            return row;
        });

        statusColumn.setCellFactory(FinesTableUtil.getStatusCellFactory());

        finesTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    class FinesTableUtil {

        public static Callback<TableColumn<Fine, String>, TableCell<Fine, String>> getStatusCellFactory() {
            return column -> new TableCell<>() {
                @Override
                protected void updateItem(String status, boolean empty) {
                    super.updateItem(status, empty);
                    if (empty || status == null) {
                        setText(null);
                        setStyle("");
                    } else {
                        setText(status);
                        switch (status.toLowerCase()) {
                            case "paid" -> setStyle("-fx-text-fill: #256029;");
                            case "unpaid" -> setStyle("-fx-text-fill: #b71c1c;");
                            default -> setStyle("");
                        }
                    }
                }
            };
        }
    }
}

