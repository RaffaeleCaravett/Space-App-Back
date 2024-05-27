package com.example.space.payloads.entities;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record PrenotazioneDTO (
        @NotNull(message = "User_id vuoto.")
        long user_id,
        @NotNull(message = "Pacchetto_id vuoto.")
        List<Long> pacchetto_id
){
}
