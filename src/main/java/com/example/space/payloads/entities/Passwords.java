package com.example.space.payloads.entities;

import jakarta.validation.constraints.NotEmpty;

public record Passwords(
        @NotEmpty(message = "Vecchia password necessaria.")
        String oldPass,
        @NotEmpty(message = "Nuova password necessaria.")
        String newPass
) {
}
