package com.example.petralibrarymanager.database.models;

import javafx.beans.property.*;

/**
 * Catalog class that combines Books and Publishers table data
 * for use with JavaFX ObservableList and TableView components.
 *
 * This class uses JavaFX Property objects to enable automatic
 * UI updates when data changes.
 */
public class Catalog {

    // Properties for JavaFX data binding
    private final StringProperty isbn;
    private final StringProperty title;
    private final StringProperty publisherName;
    private final IntegerProperty publicationYear;
    private final IntegerProperty totalCopies;
    private final IntegerProperty availableCopies;

    /**
     * Default constructor - initializes all properties with empty/zero values
     */
    public Catalog() {
        this.isbn = new SimpleStringProperty("");
        this.title = new SimpleStringProperty("");
        this.publisherName = new SimpleStringProperty("");
        this.publicationYear = new SimpleIntegerProperty(0);
        this.totalCopies = new SimpleIntegerProperty(0);
        this.availableCopies = new SimpleIntegerProperty(0);
    }

    /**
     * Constructor with all parameters
     *
     * @param isbn The book's ISBN
     * @param title The book's title
     * @param publisherName The publisher's name
     * @param publicationYear The year the book was published
     * @param totalCopies Total number of copies in the library
     * @param availableCopies Number of copies currently available
     */
    public Catalog(String isbn, String title, String publisherName,
                   int publicationYear, int totalCopies, int availableCopies) {
        this.isbn = new SimpleStringProperty(isbn);
        this.title = new SimpleStringProperty(title);
        this.publisherName = new SimpleStringProperty(publisherName);
        this.publicationYear = new SimpleIntegerProperty(publicationYear);
        this.totalCopies = new SimpleIntegerProperty(totalCopies);
        this.availableCopies = new SimpleIntegerProperty(availableCopies);
    }

    // ISBN getter, setter, and property
    public String getIsbn() {
        return isbn.get();
    }

    public void setIsbn(String isbn) {
        this.isbn.set(isbn);
    }

    public StringProperty isbnProperty() {
        return isbn;
    }

    // Title getter, setter, and property
    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public StringProperty titleProperty() {
        return title;
    }

    // Publisher Name getter, setter, and property
    public String getPublisherName() {
        return publisherName.get();
    }

    public void setPublisherName(String publisherName) {
        this.publisherName.set(publisherName);
    }

    public StringProperty publisherNameProperty() {
        return publisherName;
    }

    // Publication Year getter, setter, and property
    public int getPublicationYear() {
        return publicationYear.get();
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear.set(publicationYear);
    }

    public IntegerProperty publicationYearProperty() {
        return publicationYear;
    }

    // Total Copies getter, setter, and property
    public int getTotalCopies() {
        return totalCopies.get();
    }

    public void setTotalCopies(int totalCopies) {
        this.totalCopies.set(totalCopies);
    }

    public IntegerProperty totalCopiesProperty() {
        return totalCopies;
    }

    // Available Copies getter, setter, and property
    public int getAvailableCopies() {
        return availableCopies.get();
    }

    public void setAvailableCopies(int availableCopies) {
        this.availableCopies.set(availableCopies);
    }

    public IntegerProperty availableCopiesProperty() {
        return availableCopies;
    }

    /**
     * Checks if the book is available for checkout
     *
     * @return true if available copies > 0, false otherwise
     */
    public boolean isAvailable() {
        return getAvailableCopies() > 0;
    }

    /**
     * Checks if all copies are currently checked out
     *
     * @return true if no copies are available, false otherwise
     */
    public boolean isCheckedOut() {
        return getAvailableCopies() == 0;
    }

    /**
     * Gets the percentage of books currently available
     *
     * @return percentage as a double (0.0 to 1.0)
     */
    public double getAvailabilityPercentage() {
        if (getTotalCopies() == 0) {
            return 0.0;
        }
        return (double) getAvailableCopies() / getTotalCopies();
    }

    /**
     * Updates the available copies (useful for checkout/return operations)
     *
     * @param change positive for returns, negative for checkouts
     * @return true if update was successful, false if it would result in invalid state
     */
    public boolean updateAvailableCopies(int change) {
        int newAvailable = getAvailableCopies() + change;

        // Validate the new value
        if (newAvailable < 0 || newAvailable > getTotalCopies()) {
            return false;
        }

        setAvailableCopies(newAvailable);
        return true;
    }

    @Override
    public String toString() {
        return String.format(
                "Catalog{isbn='%s', title='%s', publisher='%s', year=%d, total=%d, available=%d}",
                getIsbn(), getTitle(), getPublisherName(),
                getPublicationYear(), getTotalCopies(), getAvailableCopies()
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Catalog catalog = (Catalog) obj;
        return getIsbn().equals(catalog.getIsbn());
    }

    @Override
    public int hashCode() {
        return getIsbn().hashCode();
    }
}
