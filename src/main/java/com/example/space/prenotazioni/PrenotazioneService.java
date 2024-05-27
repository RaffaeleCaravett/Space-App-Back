package com.example.space.prenotazioni;

import com.example.space.exceptions.BadRequestException;
import com.example.space.pacchetti.Pacchetto;
import com.example.space.pacchetti.PacchettoRepository;
import com.example.space.payloads.entities.PrenotazioneDTO;
import com.example.space.user.User;
import com.example.space.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PrenotazioneService {
    @Autowired
    PrenotazioneRepository prenotazioneRepository;
@Autowired
    UserRepository userRepository;
@Autowired
    PacchettoRepository pacchettoRepository;

    public Prenotazione save(PrenotazioneDTO prenotazioneDTO){
Prenotazione prenotazione = new Prenotazione();
        User user = userRepository.findById(prenotazioneDTO.user_id()).orElseThrow(()->new BadRequestException("User con id " + prenotazioneDTO.user_id() + " non trovato in db."));
prenotazione.setUser(user);
        List<Pacchetto> pacchettos= new ArrayList<>();
for(Long l : prenotazioneDTO.pacchetto_id()){
    pacchettos.add(pacchettoRepository.findById(l).orElseThrow(()-> new BadRequestException("Pacchetto con id " + l + " non trovato in db.")));
}

prenotazione.setPacchettos(pacchettos);
return prenotazioneRepository.save(prenotazione);
    }


    public Prenotazione getById(long id){
        return 
    }

}
