package com.kannan.service;

import com.kannan.model.Level;
import com.kannan.model.SeatHold;
import com.kannan.repository.LevelRepository;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;
import java.util.stream.Stream;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class TicketServiceImplTest {

    private TicketService ticketService;
    private LevelRepository levelRepository;
    private SeatHolderService seatHolderService;

    @Before
    public void setUp() throws Exception {
        seatHolderService = mock(SeatHolderService.class);
        levelRepository = mock(LevelRepository.class);
        ticketService = new TicketServiceImpl(levelRepository, seatHolderService);
    }

    @Test
    public void shouldUseLevelRepositoryForNumSeats() throws Exception {
        Optional<Integer> levelId = Optional.of(1);
        when(levelRepository.seatCount(levelId)).thenReturn(1000);
        int actualSeats = ticketService.numSeatsAvailable(levelId);

        assertEquals(1000, actualSeats);
    }

    @Test
    public void shouldReserveTicketIfSeatIsHeld() throws Exception {
        int seatHoldId = 1;
        int level = 3;
        Stream<Level> levels = Stream.of(new Level(3, "Balcony", 50.0, 5, 100));
        when(seatHolderService.isSeatHeld(seatHoldId)).thenReturn(true);
        when(seatHolderService.find(seatHoldId)).thenReturn(new SeatHold(level, "cust@test.com", 5, DateTime.now()));
        when(levelRepository.get(Optional.of(level))).thenReturn(levels);
        String reservationId = ticketService.reserveSeats(seatHoldId, "cust@test.com");

        verify(seatHolderService, times(1)).find(seatHoldId);
        assertNotNull(reservationId);
    }

    @Test
    public void shouldNotReserveIfSeatisNotHeld() throws Exception {
        int seatHoldId = 10;
        when(seatHolderService.isSeatHeld(seatHoldId)).thenReturn(false);

        String reservationId = ticketService.reserveSeats(seatHoldId, "cust3@test.com");

        assertNull(reservationId);
    }

    @Test
    public void shouldFindHoldSeats() throws Exception {
        when(levelRepository.seatCount(Optional.of(2))).thenReturn(2000);
        when(levelRepository.seatCount(Optional.of(3))).thenReturn(1500);
        int numSeats = 10;
        String customer = "cust@test.com";

        ticketService.findAndHoldSeats(numSeats, Optional.of(2), Optional.of(3), customer);

        verify(seatHolderService).holdSeats(eq(2), eq(numSeats), eq(customer));
    }

    @Test
    public void shouldNotHoldSeatsIfNotAvailable() throws Exception {
        when(levelRepository.seatCount(Optional.of(1))).thenReturn(10);
        when(levelRepository.seatCount(Optional.of(2))).thenReturn(20);
        when(levelRepository.seatCount(Optional.of(3))).thenReturn(30);
        when(levelRepository.seatCount(Optional.of(4))).thenReturn(40);
        int numSeats = 1000;
        String customer = "cust@test.com";

        ticketService.findAndHoldSeats(numSeats, Optional.of(1), Optional.of(4), customer);

        verifyNoMoreInteractions(seatHolderService);
    }

}