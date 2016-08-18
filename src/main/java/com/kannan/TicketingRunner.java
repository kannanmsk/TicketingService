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
        System.out.println("Loading MockData using MockDataLoader");
        loader.loadData();
        System.out.println("Loading Complete");
        System.out.println("Number of Seats Available for Level 1 - Orchestra :");
        System.out.println(ticketService.numSeatsAvailable(Optional.of(1)));
        System.out.println("Holding 5 seats for a customer - cust@test.com");
        SeatHold seatHold = ticketService.findAndHoldSeats(5, Optional.of(1), Optional.of(1), "cust@test.com");
        System.out.println("Number of Seats Available for Level 1 - Orchestra - After Seat Hold is done :");
        System.out.println(ticketService.numSeatsAvailable(Optional.of(1)));
        System.out.println("Reservation being done for the seats held");
        String id = ticketService.reserveSeats(seatHold.getSeatHoldId(), "cust@test.com");
        System.out.println("Reservation complete!");
        System.out.println("Reservation ID : ");
        System.out.println(id);
    }
}
