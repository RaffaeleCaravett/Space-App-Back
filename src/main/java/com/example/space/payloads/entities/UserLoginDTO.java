package com.example.space.payloads.entities;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public record UserLoginDTO(
        @NotEmpty(message = "Email necessaria")
        @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$", message = "Prova ad inserira una email tipo : 'a@a.com'")
        String email,
        @NotEmpty(message = "Password necessaria")
        @Length(min = 6,message = "Lunghezza minima della password : sei caratteri.")
        String password
) {
}
