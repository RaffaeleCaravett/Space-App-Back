package com.example.space.exceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class UnauthorizedException extends  RuntimeException{
    private String message;

}
