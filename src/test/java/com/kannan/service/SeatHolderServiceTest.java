package com.kannan.service;

import com.kannan.exception.SeatNotAvailableException;
import com.kannan.model.Level;
import com.kannan.model.SeatHold;
import com.kannan.repository.LevelRepository;
import org.joda.time.DateTimeUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;


public class SeatHolderServiceTest {

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Test
    public void shouldHoldSeatsWhenSeatsAreAvailable() throws Exception {
        LevelRepository levelRepository = mock(LevelRepository.class);
        Level level = new Level(1, "Orchestra", 100.0, 25, 50);
        when(levelRepository.get(Optional.of(1))).thenReturn(Stream.of(level));
        when(levelRepository.seatCount(Optional.of(1))).thenReturn(1250);
        SeatHolderService seatHolderService = new SeatHolderService(levelRepository);
        seatHolderService.setExpiryTime(1);

        SeatHold seatHold = seatHolderService.holdSeats(1, 5, "foo");

        List<SeatHold> allSeatHolds = seatHolderService.getAllSeatHolds();
        assertEquals(1, allSeatHolds.size());
        assertEquals(seatHold, allSeatHolds.get(0));
        assertEquals(1245, level.availableSeats());
        assertEquals(5, level.getHeldSeats());
    }

    @Test
    public void shouldThrowExceptionIfSeatNotAvailable() throws Exception {
        LevelRepository levelRepository = mock(LevelRepository.class);
        when(levelRepository.seatCount(Optional.of(1))).thenReturn(1);
        SeatHolderService seatHolderService = new SeatHolderService(levelRepository);

        exception.expect(SeatNotAvailableException.class);
        exception.expectMessage("Seats wanted 5, but only 1 available");

        seatHolderService.holdSeats(1, 5, "cust@test.com");
    }

    @Test
    public void shouldReturnFalseWhenSeatNotHeld() {
        LevelRepository levelRepository = mock(LevelRepository.class);
        SeatHolderService seatHolderService = new SeatHolderService(levelRepository);

        boolean seatHeld = seatHolderService.isSeatHeld(1);

        assertFalse(seatHeld);
    }

    @Test
    public void shouldReturnTrueWhenSeatIsHeld() throws Exception {
        LevelRepository levelRepository = mock(LevelRepository.class);
        Level level = new Level(1, "Orchestra", 100.0, 25, 50);
        when(levelRepository.get(Optional.of(1))).thenReturn(Stream.of(level));
        when(levelRepository.seatCount(Optional.of(1))).thenReturn(1250);
        SeatHolderService seatHolderService = new SeatHolderService(levelRepository);
        seatHolderService.setExpiryTime(1);

        SeatHold seatHold = seatHolderService.holdSeats(1, 5, "foo");

        boolean seatHeld = seatHolderService.isSeatHeld(seatHold.getSeatHoldId());
        assertTrue(seatHeld);
    }

    @Test
    public void shouldUnHoldSeat() throws Exception {
        LevelRepository levelRepository = mock(LevelRepository.class);
        Level level = new Level(1, "Orchestra", 100.0, 25, 50);
        Optional<Integer> levelId = Optional.of(1);
        when(levelRepository.get(levelId)).thenReturn(Stream.of(level)).thenReturn(Stream.of(level));
        when(levelRepository.seatCount(levelId)).thenReturn(1250);
        SeatHolderService seatHolderService = new SeatHolderService(levelRepository);
        seatHolderService.setExpiryTime(1);

        SeatHold seatHold = seatHolderService.holdSeats(1, 5, "foo");

        seatHolderService.unHold(seatHold);

        assertFalse(seatHolderService.isSeatHeld(seatHold.getSeatHoldId()));
        verify(levelRepository, times(2)).get(levelId);
        assertEquals(0, level.getHeldSeats());
        assertEquals(1250, level.availableSeats());
    }

    @Test
    public void shouldReleaseSeats() throws Exception {
        DateTimeUtils.setCurrentMillisOffset(-10000000);
        LevelRepository levelRepository = mock(LevelRepository.class);
        Level level = new Level(1, "Orchestra", 100.0, 25, 50);
        Optional<Integer> levelId = Optional.of(1);
        when(levelRepository.get(levelId)).thenReturn(Stream.of(level)).thenReturn(Stream.of(level));
        when(levelRepository.seatCount(levelId)).thenReturn(1250);
        SeatHolderService seatHolderService = new SeatHolderService(levelRepository);
        seatHolderService.setExpiryTime(1);
        seatHolderService.holdSeats(1, 5, "foo");

        DateTimeUtils.setCurrentMillisOffset(0);
        seatHolderService.releaseSeats();

        assertEquals(0, seatHolderService.getAllSeatHolds().size());
        verify(levelRepository, times(2)).get(levelId);
        assertEquals(0, level.getHeldSeats());
        assertEquals(1250, level.availableSeats());
    }
}