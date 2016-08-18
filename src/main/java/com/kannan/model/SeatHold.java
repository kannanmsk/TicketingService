package com.kannan.model;

import org.joda.time.DateTime;

import java.util.concurrent.atomic.AtomicInteger;

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

    public int getSeatHoldId() {
        return seatHoldId;
    }

    public int getLevelId() {
        return levelId;
    }

    public String getCustomerEmail() {
        return customerEmail;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public DateTime getHeldSince() {
        return heldSince;
    }
}
