package com.kannan.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class LevelTest {

    private Level balcony;

    @Before
    public void setUp() throws Exception {
        balcony = new Level(1, "Balcony", 10.0, 4, 10);
    }

    @Test
    public void isSeatsAvailable() throws Exception {
        assertEquals(true, balcony.isSeatsAvailable(5));
        assertEquals(false, balcony.isSeatsAvailable(500));
    }

    @Test
    public void availableSeats() throws Exception {
        assertEquals(40, balcony.availableSeats());
    }

    @Test
    public void incrementHold() throws Exception {
        balcony.incrementHold(5);

        assertEquals(35, balcony.availableSeats());
    }

    @Test
    public void decrementHold() throws Exception {
        balcony.incrementHold(5);
        balcony.decrementHold(3);

        assertEquals(38, balcony.availableSeats());
    }

    @Test
    public void incrementReservation() throws Exception {
        balcony.incrementReservation(5);

        assertEquals(35, balcony.availableSeats());
    }

}