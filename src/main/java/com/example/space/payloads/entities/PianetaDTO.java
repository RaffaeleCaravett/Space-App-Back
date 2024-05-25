package com.example.space.payloads.entities;

import jakarta.validation.constraints.NotEmpty;

public record PianetaDTO(
        @NotEmpty(message = "Nome necessario")
        String nome,
        @NotEmpty(message = "Galassia necessaria")
        String galassia
) {
}
