package com.kannan.model;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SeatHoldTest {

    @Test
    public void shouldIncrementId() throws Exception {
        SeatHold first = new SeatHold(1, "cust1@test.com", 2, DateTime.now());
        SeatHold second = new SeatHold(1, "cust2@test.com", 6, DateTime.now());
        int firstId = first.getSeatHoldId();
        int secondId = second.getSeatHoldId();

        assertEquals(1, secondId - firstId);
    }

}