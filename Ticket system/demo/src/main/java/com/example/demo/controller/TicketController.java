package com.example.demo.controller;

import com.example.demo.dto.TicketResponseDTO;
import com.example.demo.entity.Ticket;
import com.example.demo.enums.TicketStatus;
import com.example.demo.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public Ticket createTicket(@Valid @RequestBody Ticket ticket) {
        return ticketService.createTicket(ticket);
    }

    @GetMapping
    public List<Ticket> getAllTickets() {
        return ticketService.getAllTickets();
    }

    @GetMapping("/{Id}")
    public TicketResponseDTO getTicketById(@PathVariable Long Id) {
        return ticketService.getTicketById(Id);
    }

    @PutMapping("/{id}")
    public Ticket updateTicket(@PathVariable Long id, @RequestBody Ticket ticket) {
        return ticketService.updateTicket(id, ticket);
    }

    @PutMapping("/{ticketId}/assign{userId}")
    public Ticket assignTicketToUser(@PathVariable Long ticketId, @PathVariable Long userId) {
        return ticketService.assignTicketToUser(ticketId, userId);
    }

    @GetMapping("/search")
    public List<Ticket> getTicketByStatus(@RequestParam TicketStatus status) {
        return ticketService.getTicketByStatus(status);
    }

}
