package com.example.petralibrarymanager.database.models;

import java.time.LocalDate;

public class Reservation {
    private String bookTitle;
    private String borrowerName;
    private LocalDate reservationDate;
    private String status;
    private LocalDate pickupDeadline;

    public Reservation(String bookTitle, String borrowerName, LocalDate reservationDate, String status, LocalDate pickupDeadline) {
        this.bookTitle = bookTitle;
        this.borrowerName = borrowerName;
        this.reservationDate = reservationDate;
        this.status = status;
        this.pickupDeadline = pickupDeadline;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBorrowerName() {
        return borrowerName;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public String getStatus() {
        return status;
    }

    public LocalDate getPickupDeadline() {
        return pickupDeadline;
    }
}
