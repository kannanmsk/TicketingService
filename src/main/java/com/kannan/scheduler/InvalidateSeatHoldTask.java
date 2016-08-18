package com.kannan.scheduler;

import com.kannan.service.SeatHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Scheduler to release the seats which are held
 * @author Kannan Kuttalam
 *
 */
@Component
public class InvalidateSeatHoldTask {

    @Autowired
    private SeatHolderService seatHolderService;

    /**
     * Releases the held seats for every fixed time interval
     *
     */
    @Scheduled(fixedDelay = 60 * 1000)
    public void run() {
        seatHolderService.releaseSeats();
    }

}
