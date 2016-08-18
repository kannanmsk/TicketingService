package com.kannan.service;

import com.kannan.model.Level;
import com.kannan.model.Reservation;
import com.kannan.model.SeatHold;
import com.kannan.repository.LevelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

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
        return levelRepository.get(venueLevel).map(Level::availableSeats).mapToInt(i -> i).sum();
    }

    @Override
    public SeatHold findAndHoldSeats(int numSeats, Optional<Integer> minLevel, Optional<Integer> maxLevel, String customerEmail) {
        int start = minLevel.orElseGet(() -> 1);
        int end = maxLevel.orElseGet(() -> Integer.MAX_VALUE);
        for (int i = start; i <= end; i++) {
            Optional<Integer> levelId = Optional.of(i);
            int seats = numSeatsAvailable(levelId);
            if (seats >= numSeats) {
                levelRepository.get(levelId).forEach(level -> level.incrementHold(numSeats));
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

    private Reservation reserve(SeatHold seatHold, String customerEmail) {
        seatHolderService.unHold(seatHold);
        Reservation reservation = new Reservation(customerEmail, seatHold.getLevelId(), seatHold.getNumSeats());
        Optional<Integer> levelId = Optional.of(seatHold.getLevelId());
        levelRepository.get(levelId).forEach(level -> level.incrementReservation(seatHold.getNumSeats()));
        return reservation;
    }
}
