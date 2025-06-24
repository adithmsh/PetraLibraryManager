package com.example.petralibrarymanager.database.models;

import javafx.beans.property.*;

public class Circulation {
    private final StringProperty bookTitle;
    private final StringProperty borrowerName;
    private final ObjectProperty<java.time.LocalDate> loanDate;
    private final ObjectProperty<java.time.LocalDate> dueDate;
    private final ObjectProperty<java.time.LocalDate> returnedDate;
    private final StringProperty status;

    public Circulation(String bookTitle, String borrowerName, java.time.LocalDate loanDate,
                       java.time.LocalDate dueDate, java.time.LocalDate returnedDate) {
        this.bookTitle = new SimpleStringProperty(bookTitle);
        this.borrowerName = new SimpleStringProperty(borrowerName);
        this.loanDate = new SimpleObjectProperty<>(loanDate);
        this.dueDate = new SimpleObjectProperty<>(dueDate);
        this.returnedDate = new SimpleObjectProperty<>(returnedDate);
        this.status = new SimpleStringProperty(computeStatus(dueDate, returnedDate));
    }

    private String computeStatus(java.time.LocalDate dueDate, java.time.LocalDate returnedDate) {
        if (returnedDate == null) {
            return "borrowed";
        } else if (returnedDate.isAfter(dueDate)) {
            return "overdue";
        } else {
            return "returned";
        }
    }

    public String getBookTitle() { return bookTitle.get(); }
    public StringProperty bookTitleProperty() { return bookTitle; }

    public String getBorrowerName() { return borrowerName.get(); }
    public StringProperty borrowerNameProperty() { return borrowerName; }

    public java.time.LocalDate getLoanDate() { return loanDate.get(); }
    public ObjectProperty<java.time.LocalDate> loanDateProperty() { return loanDate; }

    public java.time.LocalDate getDueDate() { return dueDate.get(); }
    public ObjectProperty<java.time.LocalDate> dueDateProperty() { return dueDate; }

    public java.time.LocalDate getReturnedDate() { return returnedDate.get(); }
    public ObjectProperty<java.time.LocalDate> returnedDateProperty() { return returnedDate; }

    public String getStatus() { return status.get(); }
    public StringProperty statusProperty() { return status; }
}
