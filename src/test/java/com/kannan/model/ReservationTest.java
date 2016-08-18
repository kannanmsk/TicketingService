package com.kannan.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ReservationTest {
    @Test
    public void reservationIdShouldBeGenerated() throws Exception {
        Reservation reservation = new Reservation("cust@test.com", 1, 10);

        String reservationId = reservation.getReservationId();
        assertNotNull(reservationId);
        assertEquals(36, reservationId.length());
    }
}