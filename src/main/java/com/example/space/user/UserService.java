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
    PasswordEncoder bcrypt;


    public User getById(long id){
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato in db."));
    }

    public List<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }

    public User putById(long id, UserRegistrationDTO userRegistrationDTO){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato in db."));
        user.setEmail(userRegistrationDTO.email());
        user.setEta(userRegistrationDTO.eta());
        user.setNome(userRegistrationDTO.nome());
        user.setCognome(userRegistrationDTO.cognome());

        return userRepository.save(user);
    }


    public boolean changePassword(long id,String oldPass, String newPass){
       User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User con id " + id + " non trovato in db."));
        if(bcrypt.matches(oldPass, user.getPassword()))  {
            user.setPassword(newPass);
            return true;
        } else {
            throw new UnauthorizedException("Le tue vecchie password non combaciano.");
        }
    }

    public boolean deleteById(long id){
        try {
            userRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public List<User> getAll(){
        return userRepository.findAll();
    }


}
