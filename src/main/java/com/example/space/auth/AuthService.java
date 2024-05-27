package com.example.space.auth;

import com.example.space.enums.Role;
import com.example.space.exceptions.BadRequestException;
import com.example.space.exceptions.UnauthorizedException;
import com.example.space.payloads.entities.Token;
import com.example.space.payloads.entities.UserLoginDTO;
import com.example.space.payloads.entities.UserRegistrationDTO;
import com.example.space.security.JWTTools;
import com.example.space.user.User;
import com.example.space.user.UserRepository;
import com.example.space.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class AuthService {
    @Autowired
    private UserService usersService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTTools jwtTools;

    @Autowired
    private PasswordEncoder bcrypt;


    public Token authenticateUser(UserLoginDTO body) throws Exception {
        // 1. Verifichiamo che l'email dell'utente sia nel db
        List<User> users =usersService.findByEmail(body.email());
        User user = new User();
        if(users.size() == 1){
            user = users.get(0);
        }else{
         throw new BadRequestException("User con email : " + body.email() + " non presente in db.");
        }
        // 2. In caso affermativo, verifichiamo se la password corrisponde a quella trovata nel db
        if(bcrypt.matches(body.password(), user.getPassword()))  {
            // 3. Se le credenziali sono OK --> Genero un JWT e lo restituisco
            return jwtTools.createToken(user);
        } else {
            // 4. Se le credenziali NON sono OK --> 401
            throw new UnauthorizedException("Credenziali non valide!");
        }
    }


    public User registerUser(UserRegistrationDTO body) throws IOException {

        // verifico se l'email è già utilizzata
      if(!userRepository.findByEmail(body.email()).isEmpty()) {
            throw new BadRequestException("L'email " + body.email() + " è già utilizzata!");
        };
        User newUser = new User();
        newUser.setPassword(bcrypt.encode(body.password()));
        newUser.setEmail(body.email());
        newUser.setNome(body.nome());
        newUser.setCognome(body.cognome());
        newUser.setEta(body.eta());
        newUser.setRole(Role.USER);
        userRepository.save(newUser);

        return newUser;
    }
    public Page<User> getUtenti(int page, int size, String orderBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(orderBy));

        return userRepository.findAll(pageable);
    }

}