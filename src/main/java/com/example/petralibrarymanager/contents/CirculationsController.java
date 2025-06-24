package com.example.petralibrarymanager.contents;

import com.example.petralibrarymanager.database.models.Circulation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.time.LocalDate;

import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.Callback;


public class CirculationsController {

    @FXML private TableView<Circulation> circulationTableView;
    @FXML private TableColumn<Circulation, String> bookTitleColumn;
    @FXML private TableColumn<Circulation, String> borrowerNameColumn;
    @FXML private TableColumn<Circulation, LocalDate> loanDateColumn;
    @FXML private TableColumn<Circulation, LocalDate> dueDateColumn;
    @FXML private TableColumn<Circulation, LocalDate> returnedDateColumn;
    @FXML private TableColumn<Circulation, String> statusColumn;

    private final ObservableList<Circulation> circulations = FXCollections.observableArrayList();

    @FXML public void initialize() {
        bookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        borrowerNameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerName"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("loanDate"));
        dueDateColumn.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        returnedDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnedDate"));

        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));


        // Dummy data for testing
        circulations.addAll(
                new Circulation("The Great Gatsby", "Alice", LocalDate.now().minusDays(7), LocalDate.now(), null),
                new Circulation("1984", "Bob", LocalDate.now().minusDays(10), LocalDate.now().minusDays(3), LocalDate.now().minusDays(1)),
                new Circulation("Brave New World", "Charlie", LocalDate.now().minusDays(15), LocalDate.now().minusDays(5), LocalDate.now().minusDays(2)),
                new Circulation("Animal Farm", "Diana", LocalDate.now().minusDays(12), LocalDate.now().minusDays(2), LocalDate.now().minusDays(1))
        );

        statusColumn.setCellFactory(TableViewUtil.getCirculationStatusCellFactory());

        circulationTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        circulationTableView.setItems(circulations);
    }


    class TableViewUtil {

        public static Callback<TableColumn<Circulation, String>, TableCell<Circulation, String>> getCirculationStatusCellFactory() {
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
                            case "borrowed" -> text.setFill(Color.ORANGE);
                            case "returned" -> text.setFill(Color.GREEN);
                            case "overdue" -> text.setFill(Color.RED);
                            default -> text.setFill(Color.GRAY);
                        }

                        setGraphic(text);
                        setText(null); // Hide default text
                    }
                }
            };
        }
    }
}

