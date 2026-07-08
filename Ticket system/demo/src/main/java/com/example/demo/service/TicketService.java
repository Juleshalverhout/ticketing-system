package com.example.demo.service;

import com.example.demo.dto.TicketRequestDTO;
import com.example.demo.dto.TicketResponseDTO;
import com.example.demo.entity.Ticket;
import com.example.demo.entity.User;
import com.example.demo.enums.TicketStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.TicketRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;

    private final UserRepository userRepository;

    public TicketService(TicketRepository ticketRepository, UserRepository userRepository) {
        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
    }

    public TicketResponseDTO createTicket(TicketRequestDTO requestDTO) {
        Ticket newTicket = new Ticket();

        newTicket.setTitle(requestDTO.title());
        newTicket.setDescription(requestDTO.description());
        newTicket.setStatus(TicketStatus.OPEN);

        if (requestDTO.assigneeId() != null) {
            User assignee = userRepository.findById(requestDTO.assigneeId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + requestDTO.assigneeId()));
            newTicket.setAssignee(assignee);
        }

        Ticket savedTicket = ticketRepository.save(newTicket);
        return convertToDTO(savedTicket);
    }


    public List<TicketResponseDTO> getAllTickets() {

        List<Ticket> rawTickets = ticketRepository.findAll();
        List<TicketResponseDTO> dtoList = new java.util.ArrayList<>();

        for (Ticket ticket : rawTickets) {
            dtoList.add(convertToDTO(ticket));
        }
        return dtoList;
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


    public List<TicketResponseDTO> getTicketByStatus(TicketStatus status) {

        List<Ticket> rawTickets = ticketRepository.findByStatus(status);
        List<TicketResponseDTO> dtoList = new ArrayList<>();

        for (Ticket ticket : rawTickets) {
            dtoList.add(convertToDTO(ticket));
        }
        return dtoList;
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

