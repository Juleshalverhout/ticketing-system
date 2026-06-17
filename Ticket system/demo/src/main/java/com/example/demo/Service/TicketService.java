package com.example.demo.Service;

import com.example.demo.Entity.Ticket;
import com.example.demo.Entity.User;
import com.example.demo.Enums.TicketStatus;
import com.example.demo.Repository.TicketRepository;
import com.example.demo.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public Ticket createTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }

    public Ticket getTicketById(Long id) {
        return ticketRepository.findById(id).orElse(null);
    }

    public Ticket updateTicket(Long id, Ticket updatedTicketData) {
        Ticket existingTicket = ticketRepository.findById(id).orElse(null);

        if (existingTicket != null) {
            existingTicket.setTitle(updatedTicketData.getTitle());
            existingTicket.setDescription(updatedTicketData.getDescription());
            existingTicket.setStatus(updatedTicketData.getStatus());

            return ticketRepository.save(existingTicket);
        }

        return null;
    }

    public Ticket assignTicketToUser(Long ticketId, Long userId) {

        Ticket ticket = ticketRepository.findById(ticketId).orElse(null);
        User user = userRepository.findById(userId).orElse(null);

        if (ticket != null && user != null) {
            ticket.setAssignee(user);
            return ticketRepository.save(ticket);
        }
        return null;

    }

    public List<Ticket> getTicketByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }
}
