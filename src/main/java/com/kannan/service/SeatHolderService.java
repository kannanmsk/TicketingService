package com.kannan.service;

import com.kannan.exception.SeatNotAvailableException;
import com.kannan.model.SeatHold;
import com.kannan.repository.LevelRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;

import static java.lang.String.format;

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

    public SeatHold holdSeats(int levelId, int numSeats, String customerEmail) throws SeatNotAvailableException {
        synchronized (this) {
            int available = levelRepository.seatCount(Optional.of(levelId));
            if (available >= numSeats) {
                SeatHold seatHold = new SeatHold(levelId, customerEmail, numSeats, DateTime.now());
                seatHolds.put(seatHold.getSeatHoldId(), seatHold);
                levelRepository.get(Optional.of(levelId)).forEach(level -> level.incrementHold(numSeats));
                return seatHold;
            }
            throw new SeatNotAvailableException(format("Seats wanted %d, but only %d available", numSeats, available));
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

    public void setExpiryTime(int expiryTime) {
        this.expiryTime = expiryTime;
    }

    public List<SeatHold> getAllSeatHolds() {
        return new ArrayList<>(seatHolds.values());
    }
}