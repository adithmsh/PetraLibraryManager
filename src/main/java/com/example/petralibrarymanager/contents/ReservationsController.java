package com.example.petralibrarymanager.contents;

import com.example.petralibrarymanager.database.models.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.util.Callback;
import javafx.scene.paint.Color;

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

public class ReservationsController {

    @FXML private TableView<Reservation> reservationsTable;
    @FXML private TableColumn<Reservation, String> bookTitleColumn;
    @FXML private TableColumn<Reservation, String> borrowerNameColumn;
    @FXML private TableColumn<Reservation, LocalDate> reservationDateColumn;
    @FXML private TableColumn<Reservation, String> statusColumn;
    @FXML private TableColumn<Reservation, LocalDate> pickupDeadlineColumn;

    @FXML
    public void initialize() {
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowerNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        reservationDateColumn.setCellValueFactory(new PropertyValueFactory<>("reservationDate"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        pickupDeadlineColumn.setCellValueFactory(new PropertyValueFactory<>("pickupDeadline"));

        reservationsTable.setItems(getDummyReservations());

        // Set up right-click context menu for each row
        reservationsTable.setRowFactory(tv -> {
            TableRow<Reservation> row = new TableRow<>();
            ContextMenu contextMenu = new ContextMenu();

            MenuItem changeStatusItem = new MenuItem("Change Status");
            changeStatusItem.setOnAction(e -> {
                Reservation selected = row.getItem();
                if (selected != null) {
                    showChangeStatusDialog(selected);
                }
            });

            MenuItem deleteItem = new MenuItem("Delete");
            deleteItem.setOnAction(e -> {
                Reservation selected = row.getItem();
                if (selected != null) {
                    reservationsTable.getItems().remove(selected);
                    // Optionally delete from database
                }
            });

            contextMenu.getItems().addAll(changeStatusItem, deleteItem);

            row.contextMenuProperty().bind(
                    javafx.beans.binding.Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(contextMenu)
            );

            return row;
        });

        statusColumn.setCellFactory(TableViewUtil.getReservationStatusCellFactory());


        reservationsTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private void showChangeStatusDialog(Reservation reservation) {
        String currentStatus = reservation.getStatus();
        ChoiceDialog<String> dialog;

        switch (currentStatus.toLowerCase()) {
            case "pending":
                dialog = new ChoiceDialog<>("cancelled", "cancelled");
                break;
            case "ready":
                dialog = new ChoiceDialog<>("fulfilled", "fulfilled", "cancelled");
                break;
            default:
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Change Status");
                alert.setHeaderText(null);
                alert.setContentText("No status change allowed for: " + currentStatus);
                alert.showAndWait();
                return;
        }

        dialog.setTitle("Change Reservation Status");
        dialog.setHeaderText("Current status: " + currentStatus);
        dialog.setContentText("Choose new status:");

        dialog.showAndWait().ifPresent(newStatus -> {
            reservation.setStatus(newStatus);
            reservationsTable.refresh();
            // Save to DB if needed
        });
    }


    private ObservableList<Reservation> getDummyReservations() {
        return FXCollections.observableArrayList(
                new Reservation("Clean Code", "Adit", LocalDate.now().minusDays(2), "Pending", LocalDate.now().plusDays(2)),
                new Reservation("Effective Java", "Budi", LocalDate.now().minusDays(4), "Ready", LocalDate.now().plusDays(1)),
                new Reservation("Design Patterns", "Citra", LocalDate.now().minusDays(1), "Pending", LocalDate.now().plusDays(3)),
                new Reservation("Java Concurrency", "Dina", LocalDate.now().minusDays(3), "Fulfilled", LocalDate.now()),
                new Reservation("Refactoring", "Eka", LocalDate.now().minusDays(5), "Cancelled", LocalDate.now().minusDays(1)),
                new Reservation("Domain-Driven Design", "Farhan", LocalDate.now().minusDays(6), "Ready", LocalDate.now().plusDays(2)),
                new Reservation("The Pragmatic Programmer", "Gita", LocalDate.now().minusDays(2), "Pending", LocalDate.now().plusDays(4)),
                new Reservation("Spring in Action", "Hendra", LocalDate.now().minusDays(3), "Fulfilled", LocalDate.now().minusDays(1)),
                new Reservation("Head First Java", "Intan", LocalDate.now().minusDays(1), "Pending", LocalDate.now().plusDays(5)),
                new Reservation("Introduction to Algorithms", "Joko", LocalDate.now().minusDays(7), "Ready", LocalDate.now().plusDays(3))
        );
    }

    public class TableViewUtil {

        // Existing method for Circulation...

        public static Callback<TableColumn<Reservation, String>, TableCell<Reservation, String>> getReservationStatusCellFactory() {
            return column -> new TableCell<>() {
                @Override
                protected void updateItem(String status, boolean empty) {
                    super.updateItem(status, empty);

                    if (empty || status == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        Text text = new Text(status);
                        switch (status.toLowerCase()) {
                            case "pending" -> text.setFill(Color.ORANGE);
                            case "ready" -> text.setFill(Color.GREEN);
                            case "cancelled" -> text.setFill(Color.RED);
                            default -> text.setFill(Color.GRAY);
                        }

                        setGraphic(text);
                        setText(null); // prevent double text rendering
                    }
                }
            };
        }
    }
}

