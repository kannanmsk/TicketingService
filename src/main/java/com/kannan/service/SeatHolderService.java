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

/**
 * Seat holder service to hold and release seats
 * @author Kannan Kuttalam
 *
 */
@Component
public class SeatHolderService {

    private final LevelRepository levelRepository;

    @Autowired
    public SeatHolderService(LevelRepository levelRepository) {
        this.levelRepository = levelRepository;
    }

    @Value("${expiry_time}")
    private int expiryTime;

    //HashMap to have the seat hold information
    private Map<Integer, SeatHold> seatHolds = new HashMap<>();

    /**
     * Release the held seats
     *
     */
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

    /**
     * To un-hold the held seat
     *
     * @param seatHold seat hold object of the seat held
     */
    public void unHold(SeatHold seatHold) {
        seatHolds.remove(seatHold.getSeatHoldId());
        int numSeats = seatHold.getNumSeats();
        Optional<Integer> levelId = Optional.of(seatHold.getLevelId());
        levelRepository.get(levelId).forEach(level -> level.decrementHold(numSeats));
    }

    /**
     * To check whether the seat held time is expired
     *
     * @param heldSince time since which the seat is held
     * @return whether the time is expired or not
     */
    private boolean isExpired(DateTime heldSince) {
        return DateTime.now().getMillis() - heldSince.getMillis() > expiryInMillis();
    }

    /**
     * Returns the expiry time in milliseconds
     *
     * @return the exipry time in millis
     */
    private int expiryInMillis() {
        return expiryTime * 60 * 1000;
    }

    /**
     * Hold the seats
     *
     * @param levelId level Id where the seats are to be held
     * @param numSeats number of seats to be held
     * @param customerEmail email id of the customer
     * @return the seat hold object of the seat held
     */
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

    /**
     * To check whether the seat is held
     *
     * @param seatHoldId seat hold id of the seats to be checked
     * @return whether seat is held or not
     */
    public boolean isSeatHeld(int seatHoldId) {
        synchronized (this) {
            return seatHolds.containsKey(seatHoldId);
        }
    }

    /**
     * To find the seat held based on the seat hold id
     *
     * @param seatHoldId seat hold id of the seats held
     * @return the seat hold object based on the seat hold id passed
     */
    public SeatHold find(int seatHoldId) {
        synchronized (this) {
            return seatHolds.get(seatHoldId);
        }
    }

    /**
     * To set the expiry time
     *
     * @param expiryTime expiry time to be set
     */
    public void setExpiryTime(int expiryTime) {
        this.expiryTime = expiryTime;
    }

    /**
     * Get all the seatholds
     *
     * @return list of all the seat holds
     */
    public List<SeatHold> getAllSeatHolds() {
        return new ArrayList<>(seatHolds.values());
    }
}