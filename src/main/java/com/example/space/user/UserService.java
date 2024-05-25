package com.example.space.user;

import com.example.space.exceptions.NotFoundException;

import com.example.space.payloads.entities.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;



    private User getById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato in db."));
    }

    private List<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    private User putById(long id, UserRegistrationDTO userRegistrationDTO){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato in db."));
        user
    }

}
