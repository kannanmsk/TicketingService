package com.kannan.model;

import java.util.UUID;

/**
 * Reservation model to have each reservation information
 * @author Kannan Kuttalam
 *
 */
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

    /**
     * get the reservation Id
     *
     * @return reservation Id of the reservation made
     */
    public String getReservationId() {
        return reservationId;
    }

    /**
     * get the customer email
     *
     * @return customer email who made the reservation
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * get the level Id reserved
     *
     * @return the level Id reserved
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * get the number of seats reserved
     *
     * @return count of seats reserved
     */
    public int getNumSeats() {
        return numSeats;
    }
}