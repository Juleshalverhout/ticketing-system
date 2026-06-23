package com.example.demo.dto;

import com.example.demo.enums.TicketStatus;

public record TicketResponseDTO(
        Long id,
        String title,
        String description,
        TicketStatus status,
        String assigneeName
) {
}
