package com.example.space.payloads.entities;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

public record PacchettoDTO(
        @NotNull(message="Prezzo vuoto.")
        double prezzo,

        @NotNull(message="Posti vuoti.")
        int posti,

        @NotNull(message="Data da vuota.")
        List<Integer> da,

    @NotNull(message="Data a vuota.")
        List<Integer> a,
        @NotNull(message = "Pianeta id vuoto.")
        List<Long> pianeta_id
) {

}
