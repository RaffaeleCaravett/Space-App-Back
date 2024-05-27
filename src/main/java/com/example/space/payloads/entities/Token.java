package com.example.space.payloads.entities;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Token {
    private String accessToken;
    private String refreshToken;
}
