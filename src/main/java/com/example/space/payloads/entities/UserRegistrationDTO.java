package com.example.space.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public record UserRegistrationDTO(
        @NotEmpty(message = "Email necessaria")
        String email,
        @NotEmpty(message = "Password necessaria")
        String password,
        @NotEmpty(message = "Nome necessaria")
        String nome,
        @NotEmpty(message = "Cognome necessaria")
        String cognome,
        @NotNull(message = "Età necessaria")
        int eta
) {
}
