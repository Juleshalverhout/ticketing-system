package com.example.demo.dto;

public record UserResponseDTO(
        Long id,
        String username,
        String email,
        String role

) {
}
