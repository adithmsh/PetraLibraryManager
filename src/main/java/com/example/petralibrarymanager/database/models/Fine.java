package com.example.petralibrarymanager.database.models;

import java.time.LocalDate;

public class Fine {
    private String borrowerName;
    private String bookTitle;
    private double amount;
    private LocalDate dueDate;
    private String status;

    public Fine(String borrowerName, String bookTitle, double amount, LocalDate dueDate, String status) {
        this.borrowerName = borrowerName;
        this.bookTitle = bookTitle;
        this.amount = amount;
        this.dueDate = dueDate;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public double getAmount() {
        return amount;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public String getStatus() {
        return status;
    }
}

