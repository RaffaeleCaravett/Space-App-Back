package com.example.space.payloads.entities;

import jakarta.validation.constraints.NotEmpty;

public record UserLoginDTO(
        @NotEmpty(message = "Email necessaria")
        String email,
        @NotEmpty(message = "Password necessaria")
        String password
) {
}
