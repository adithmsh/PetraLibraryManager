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

public class ReservationsController {

    @FXML private TableView<Reservation> reservationTable;
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

        reservationTable.setItems(getDummyReservations());
        statusColumn.setCellFactory(TableViewUtil.getReservationStatusCellFactory());


        reservationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
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

