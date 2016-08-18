package com.kannan.model;

import java.util.UUID;

public class Reservation {
    private final String reservationId;
    private final String customerEmail;
    private final int levelId;
    private final int numSeats;

    public Reservation(String customerEmail, int levelId, int numSeats) {
        this.reservationId = UUID.randomUUID().toString();
        this.customerEmail = customerEmail;
        this.levelId = levelId;
        this.numSeats = numSeats;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getNumSeats() {
        return numSeats;
    }
}