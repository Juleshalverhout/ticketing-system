package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TicketRequestDTO(

        @NotBlank(message = "Ticket title cannot be empty")
        @Size(max = 100, message = "Title is too long")
        String title,

        @NotBlank(message = "Ticket description cannot be empty")
        String description,

        Long assigneeId
) {
}
