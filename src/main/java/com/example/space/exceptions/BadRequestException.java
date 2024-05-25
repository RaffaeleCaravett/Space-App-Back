package com.example.space.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class BadRequestException extends RuntimeException{
    private String message;
    private List<ObjectError> messageList;

public BadRequestException(List<ObjectError> messageList){
    this.messageList=messageList;
}
    public BadRequestException(String message){
        this.message=message;
    }
}
