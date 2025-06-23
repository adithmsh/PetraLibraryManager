package com.example.petralibrarymanager.contents;

import com.example.petralibrarymanager.database.DataBaseManager;
import com.example.petralibrarymanager.database.models.Catalog;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

// book dialog imports
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class CatalogController {
    @FXML private StackPane rootPane;
    @FXML private TableView catalogsTableView;
    @FXML private TableColumn<Catalog, String> ISBNColumn;
    @FXML private TableColumn<Catalog, String> titleColumn;
    @FXML private TableColumn<Catalog, String> publisherNameColumn;
    @FXML private TableColumn<Catalog, Number> publicationYearColumn;
    @FXML private TableColumn<Catalog, Number> totalCopiesColumn;
    @FXML private TableColumn<Catalog, Number> availableCopiesColumn;

    private ObservableList<Catalog> catalogs = FXCollections.observableArrayList();

    @FXML public void initialize() {
        ISBNColumn.setCellValueFactory(cellData -> cellData.getValue().isbnProperty());
        titleColumn.setCellValueFactory(cellData -> cellData.getValue().titleProperty());
        publisherNameColumn.setCellValueFactory(cellData -> cellData.getValue().publisherNameProperty());
        publicationYearColumn.setCellValueFactory(cellData -> cellData.getValue().publicationYearProperty());
        totalCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().totalCopiesProperty());
        availableCopiesColumn.setCellValueFactory(cellData -> cellData.getValue().availableCopiesProperty());

        catalogsTableView.setRowFactory(tv -> {
            TableRow<Catalog> row = new TableRow<>();

            row.setOnContextMenuRequested(event -> {
                ContextMenu contextMenu = new ContextMenu();

                MenuItem addItem = new MenuItem("Add Book");
                addItem.setOnAction(e -> addBook());

                contextMenu.getItems().add(addItem);

                // If clicked on a row with a Book
                if (!row.isEmpty()) {
                    Catalog selectedBook = row.getItem();

                    MenuItem editItem = new MenuItem("Edit Book");
                    editItem.setOnAction(e -> editBook(selectedBook));

                    MenuItem deleteItem = new MenuItem("Delete Book");
                    deleteItem.setOnAction(e -> deleteBook(selectedBook));

                    contextMenu.getItems().addAll(editItem, deleteItem);
                }

                // Show context menu
                contextMenu.show(row, event.getScreenX(), event.getScreenY());
            });

            return row;
        });


        catalogsTableView.setItems(catalogs);
        loadCatalogs();

    }

    public void loadCatalogs() {
        String query = "SELECT b.isbn, b.title, p.name AS publisher, b.publication_year, b.total_copies, b.available_copies " +
                "FROM books b JOIN publishers p ON b.publisher_id = p.publisher_id;";
        try {
                Statement stmt = DataBaseManager.conn.createStatement();
                ResultSet rs = stmt.executeQuery(query);

                while (rs.next()) {
                    String isbn = rs.getString("isbn");
                    String title = rs.getString("title");
                    String publisherName = rs.getString("publisher");
                    int publicationYear = rs.getInt("publication_year");
                    int totalCopies = rs.getInt("total_copies");
                    int availableCopies = rs.getInt("available_copies");

                    catalogs.add(new Catalog(isbn, title, publisherName, publicationYear, totalCopies, availableCopies));
                }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void showBookDialog(Catalog existingBook) {
        BookDialog dialog = new BookDialog(existingBook);
        Optional<Catalog> result = dialog.showAndWait();

        result.ifPresent(book -> {
            if (existingBook == null) {
                // Add new
                catalogsTableView.getItems().add(book);
            } else {
                // Update existing
                existingBook.setIsbn(book.getIsbn());
                existingBook.setTitle(book.getTitle());
                existingBook.setPublisherName(book.getPublisherName());
                existingBook.setPublicationYear(book.getPublicationYear());
                existingBook.setTotalCopies(book.getTotalCopies());
                existingBook.setAvailableCopies(book.getAvailableCopies());
                catalogsTableView.refresh();
            }
        });
    }


    private void addBook() {
        // Show dialog or switch view to add a book
        System.out.println("Add book clicked");
        showBookDialog(null);
    }

    private void editBook(Catalog book) {
        // Show dialog or inline editor with book data
        System.out.println("Edit book: " + book.getTitle());
        showBookDialog(book);
    }

    private void deleteBook(Catalog book) {
        // Confirm and remove from table / DB
        System.out.println("Delete book: " + book.getTitle());
    }




    class BookDialog extends Dialog<Catalog> {

        public BookDialog(Catalog bookData) {
            setTitle(bookData == null ? "Add Book" : "Edit Book");

            DialogPane dialogPane = getDialogPane();
            dialogPane.setPrefWidth(400);

            TextField isbnField = new TextField();
            TextField titleField = new TextField();
            TextField publisherField = new TextField();
            TextField yearField = new TextField();
            TextField totalCopiesField = new TextField();
            TextField availableCopiesField = new TextField();

            if (bookData != null) {
                isbnField.setText(bookData.getIsbn());
                titleField.setText(bookData.getTitle());
                publisherField.setText(bookData.getPublisherName());
                yearField.setText(String.valueOf(bookData.getPublicationYear()));
                totalCopiesField.setText(String.valueOf(bookData.getTotalCopies()));
                availableCopiesField.setText(String.valueOf(bookData.getAvailableCopies()));
            }

            GridPane grid = new GridPane();
            grid.setHgap(10);
            grid.setVgap(10);
            grid.setPadding(new Insets(20));

            grid.add(new Label("ISBN:"), 0, 0);
            grid.add(isbnField, 1, 0);
            grid.add(new Label("Title:"), 0, 1);
            grid.add(titleField, 1, 1);
            grid.add(new Label("Publisher:"), 0, 2);
            grid.add(publisherField, 1, 2);
            grid.add(new Label("Year:"), 0, 3);
            grid.add(yearField, 1, 3);
            grid.add(new Label("Total Copies:"), 0, 4);
            grid.add(totalCopiesField, 1, 4);
            grid.add(new Label("Available Copies:"), 0, 5);
            grid.add(availableCopiesField, 1, 5);

            dialogPane.setContent(grid);
            ButtonType saveButtonType = new ButtonType("Save", ButtonBar.ButtonData.OK_DONE);
            dialogPane.getButtonTypes().addAll(saveButtonType, ButtonType.CANCEL);

            setResultConverter(button -> {
                if (button == saveButtonType) {
                    return new Catalog(
                            isbnField.getText(),
                            titleField.getText(),
                            publisherField.getText(),
                            Integer.parseInt(yearField.getText()),
                            Integer.parseInt(totalCopiesField.getText()),
                            Integer.parseInt(availableCopiesField.getText())
                    );
                }
                return null;
            });
        }
    }

}
