package com.kannan.service;

import com.kannan.exception.SeatNotAvailableException;
import com.kannan.model.Reservation;
import com.kannan.model.SeatHold;
import com.kannan.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Ticket Service Implementation implementing the TicketService interface
 * @author Kannan Kuttalam
 *
 */
@Component
public class TicketServiceImpl implements TicketService {
    private LevelRepository levelRepository;
    private SeatHolderService seatHolderService;

    @Autowired
    public TicketServiceImpl(LevelRepository levelRepository, SeatHolderService seatHolderService) {
        this.levelRepository = levelRepository;
        this.seatHolderService = seatHolderService;
    }

    @Override
    public int numSeatsAvailable(Optional<Integer> venueLevel) {
        return levelRepository.seatCount(venueLevel);
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) throws SeatNotAvailableException {
        int start = minLevel.orElseGet(() -> 1);
        int end = maxLevel.orElseGet(() -> Integer.MAX_VALUE);
        for (int i = start; i <= end; i++) {
            Optional<Integer> levelId = Optional.of(i);
            int seats = numSeatsAvailable(levelId);
            if (seats >= numSeats) {
                return seatHolderService.holdSeats(i, numSeats, customerEmail);
            }
        }
        return null;
    }

    @Override
    public String reserveSeats(int seatHoldId, String customerEmail) {
        if (seatHolderService.isSeatHeld(seatHoldId)) {
            Reservation reservation = reserve(seatHolderService.find(seatHoldId), customerEmail);
            return reservation.getReservationId();
        }
        return null;
    }

    /**
     * Reserve the seats based on seat hold and customer
     *
     * @param seatHold seat held
     * @param customerEmail email of the customer
     * @return reservation id of the confirmed reservation
     */
    private Reservation reserve(SeatHold seatHold, String customerEmail) {
        seatHolderService.unHold(seatHold);
        Reservation reservation = new Reservation(customerEmail, seatHold.getLevelId(), seatHold.getNumSeats());
        Optional<Integer> levelId = Optional.of(seatHold.getLevelId());
        levelRepository.get(levelId).forEach(level -> level.incrementReservation(seatHold.getNumSeats()));
        return reservation;
    }
}