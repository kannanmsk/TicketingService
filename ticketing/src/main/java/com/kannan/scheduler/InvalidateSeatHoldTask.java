package com.kannan.scheduler;

import com.kannan.service.SeatHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class InvalidateSeatHoldTask {

    @Autowired
    private SeatHolderService seatHolderService;

    @Scheduled(fixedDelay = 60 * 1000)
    public void run() {
        seatHolderService.releaseSeats();
    }

}
