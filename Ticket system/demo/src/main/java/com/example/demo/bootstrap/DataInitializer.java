package com.example.demo.bootstrap;

import com.example.demo.dto.TicketRequestDTO;
import com.example.demo.dto.UserResponseDTO;
import com.example.demo.service.TicketService;
import com.example.demo.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import com.example.demo.entity.User;
import com.example.demo.entity.Ticket;

@Component
public class DataInitializer implements CommandLineRunner {
    private final UserService userService;
    private final TicketService ticketService;

    public DataInitializer(UserService userService, TicketService ticketService) {
        this.userService = userService;
        this.ticketService = ticketService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("--- SEEDING DATABASE WITH DUMMY DATA ---");

        UserResponseDTO alice = userService.createUser(new User("alice_admin", "alice@company.com", "ADMIN"));
        UserResponseDTO bob = userService.createUser(new User("bob_support", "bob@company.com", "SUPPORT"));

        ticketService.createTicket(new TicketRequestDTO("Server down!", "Log in failed", alice.id()));
        ticketService.createTicket(new TicketRequestDTO("Broken mouse", "Scroll wheel stuck", bob.id()));

        System.out.println("--- SEEDING COMPLETE ---");
    }
}
