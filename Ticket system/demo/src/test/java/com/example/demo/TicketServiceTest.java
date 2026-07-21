package com.example.demo;

import com.example.demo.dto.TicketResponseDTO;
import com.example.demo.entity.Ticket;
import com.example.demo.enums.TicketStatus;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.repository.TicketRepository;
import com.example.demo.service.TicketService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TicketServiceTest {

    @Mock
    private TicketRepository ticketRepository;

    @InjectMocks
    private TicketService ticketService;

    @Test
    void getTicketById_ShouldReturnDTO_WhenTicketExists() {

        Ticket ticket = new Ticket("Test title", "Test description", TicketStatus.OPEN);
        ticket.setId(1L);

        when(ticketRepository.findById(1L)).thenReturn(Optional.of(ticket));

        TicketResponseDTO result = ticketService.getTicketById(1L);

        assertNotNull(result);
        assertEquals("Test title", result.title());
        verify(ticketRepository, times(1)).findById(1L);
    }

    @Test
    void deleteTicket_ShouldCallRepository_WhenTicketExists() {
        Long ticketId = 1L;

        ticketService.deleteTicket(ticketId);

        verify(ticketRepository, times(1)).deleteById(ticketId);
    }

    @Test
    void deleteTicket_ShouldThrowException_WhenTicketDoesNotExist() {

        when(ticketRepository.existsById(99L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> ticketService.deleteTicket(99L));
    }
}
