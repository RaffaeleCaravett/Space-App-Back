package com.example.space.user;

import com.example.space.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;



    private User getById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato in db."));
    }

}
