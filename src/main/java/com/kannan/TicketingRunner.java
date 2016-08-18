package com.kannan;

import com.kannan.data.MockDataLoader;
import com.kannan.model.SeatHold;
import com.kannan.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TicketingRunner implements CommandLineRunner {

    @Autowired
    private TicketService ticketService;
    @Autowired
    private MockDataLoader loader;

    @Override
    public void run(String... args) throws Exception {
        loader.loadData();
        System.out.println(ticketService.numSeatsAvailable(Optional.of(1)));
        SeatHold seatHold = ticketService.findAndHoldSeats(5, Optional.of(1), Optional.of(1), "foo");
        System.out.println(ticketService.numSeatsAvailable(Optional.of(1)));
        String id = ticketService.reserveSeats(seatHold.getSeatHoldId(), "foo");
        System.out.println(id);
    }
}
