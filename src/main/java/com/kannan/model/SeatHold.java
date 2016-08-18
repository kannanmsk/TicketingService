package com.kannan.model;

import org.joda.time.DateTime;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SeatHold model to have seat hold information
 * @author Kannan Kuttalam
 *
 */
public class SeatHold {
    private static final AtomicInteger atomicInteger = new AtomicInteger();
    private int seatHoldId;
    private int levelId;
    private String customerEmail;
    private int numSeats;
    private DateTime heldSince;

    public SeatHold(int levelId, String customerEmail, int numSeats, DateTime heldSince) {
        this.seatHoldId = atomicInteger.getAndIncrement();
        this.levelId = levelId;
        this.customerEmail = customerEmail;
        this.numSeats = numSeats;
        this.heldSince = heldSince;
    }

    /**
     * get the seat hold id
     *
     * @return seat hold id when a seat is held
     */
    public int getSeatHoldId() {
        return seatHoldId;
    }

    /**
     * get the level Id
     *
     * @return the level id
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * get the customer email
     *
     * @return the customer email
     */
    public String getCustomerEmail() {
        return customerEmail;
    }

    /**
     * get the number of seats
     *
     * @return the number of seats
     */
    public int getNumSeats() {
        return numSeats;
    }

    /**
     * get the time since when the seat is held
     *
     * @return the time since when the seat is held
     */
    public DateTime getHeldSince() {
        return heldSince;
    }
}
