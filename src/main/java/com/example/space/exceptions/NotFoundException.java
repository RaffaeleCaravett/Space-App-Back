package com.example.space.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.ObjectError;

import java.util.List;

@Getter
@Setter
public class NotFoundException extends RuntimeException{
    private String message;
    private List<ObjectError> messageList;

    public NotFoundException(String message){
        this.message=message;
    }

    public NotFoundException(List<ObjectError> message){
        this.messageList=message;
    }
}
