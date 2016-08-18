package com.kannan.service;

import com.kannan.model.SeatHold;
import com.kannan.repository.LevelRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Component
public class SeatHolderService {

    private final LevelRepository levelRepository;

    @Autowired
    public SeatHolderService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Value("${expiry_time}")
    private int expiryTime;

    private Map<Integer, SeatHold> seatHolds = new HashMap<>();

    public void releaseSeats() {
        synchronized (this) {
            for (Map.Entry<Integer, SeatHold> holdEntry : seatHolds.entrySet()) {
                SeatHold seatHold = holdEntry.getValue();
                if (isExpired(seatHold.getHeldSince())) {
                    unHold(seatHold);
                }
            }
        }
    }

    public void unHold(SeatHold seatHold) {
        seatHolds.remove(seatHold.getSeatHoldId());
        int numSeats = seatHold.getNumSeats();
        Optional<Integer> levelId = Optional.of(seatHold.getLevelId());
        levelRepository.get(levelId).forEach(level -> level.decrementHold(numSeats));
    }

    private boolean isExpired(DateTime heldSince) {
        return DateTime.now().getMillis() - heldSince.getMillis() > expiryInMillis();
    }

    private int expiryInMillis() {
        return expiryTime * 60 * 1000;
    }

    public SeatHold holdSeats(int level, int numSeats, String customerEmail) {
        synchronized (this) {
            SeatHold seatHold = new SeatHold(level, customerEmail, numSeats, DateTime.now());
            seatHolds.put(seatHold.getSeatHoldId(), seatHold);
            return seatHold;
        }
    }

    public boolean isSeatHeld(int seatHoldId) {
        synchronized (this) {
            return seatHolds.containsKey(seatHoldId);
        }
    }

    public SeatHold find(int seatHoldId) {
        synchronized (this) {
            return seatHolds.get(seatHoldId);
        }
    }
}