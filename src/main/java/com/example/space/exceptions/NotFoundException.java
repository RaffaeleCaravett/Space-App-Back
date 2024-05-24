package com.example.space.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NotFoundException extends RuntimeException{
    private String message;
    private List<String> messageList;

    public NotFoundException(String message){
        this.message=message;
    }

    public NotFoundException(List<String> message){
        this.messageList=message;
    }
}
