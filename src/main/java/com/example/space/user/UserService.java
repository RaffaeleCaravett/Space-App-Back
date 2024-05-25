package com.example.space.user;

import com.example.space.exceptions.NotFoundException;

import com.example.space.exceptions.UnauthorizedException;
import com.example.space.payloads.entities.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private PasswordEncoder bcrypt;


    private User getById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato in db."));
    }

    private List<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    private User putById(long id, UserRegistrationDTO userRegistrationDTO){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato in db."));
        user.setEmail(userRegistrationDTO.email());
        user.setEta(userRegistrationDTO.eta());
        user.setNome(userRegistrationDTO.nome());
        user.setCognome(userRegistrationDTO.cognome());

        return userRepository.save(user);
    }


    private boolean changePassword(long id,String oldPass, String newPass){
       User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato in db."));
        if(bcrypt.matches(oldPass, user.getPassword()))  {
            user.setPassword(newPass);
            return true;
        } else {
            throw new UnauthorizedException("Le tue vecchie password non combaciano.");
        }
    }

}
