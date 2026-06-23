package com.example.demo.service;

import com.example.demo.dto.TicketResponseDTO;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.enums.TicketStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
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

    public TicketResponseDTO getTicketById(Long id) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));
        return convertToDTO(ticket);
    }

    public Ticket updateTicket(Long id, Ticket updatedTicketData) {
        Ticket existingTicket = ticketRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + id));

        existingTicket.setTitle(updatedTicketData.getTitle());
        existingTicket.setDescription(updatedTicketData.getDescription());
        existingTicket.setStatus(updatedTicketData.getStatus());

        return ticketRepository.save(existingTicket);

    }

    public Ticket assignTicketToUser(Long ticketId, Long userId) {

        Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new ResourceNotFoundException("Ticket not found with id: " + ticketId));

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        ticket.setAssignee(user);
        return ticketRepository.save(ticket);
    }


    public List<Ticket> getTicketByStatus(TicketStatus status) {
        return ticketRepository.findByStatus(status);
    }

    private TicketResponseDTO convertToDTO(Ticket ticket) {
        String assigneeName = "unassigned";

        if (ticket.getAssignee() != null) {

            assigneeName = ticket.getAssignee().getUsername();

        }

        return new TicketResponseDTO(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                assigneeName
        );
    }
}

