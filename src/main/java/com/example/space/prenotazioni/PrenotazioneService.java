package com.example.space.prenotazioni;

import com.example.space.exceptions.BadRequestException;
import com.example.space.pacchetti.Pacchetto;
import com.example.space.pacchetti.PacchettoRepository;
import com.example.space.payloads.entities.PacchettoDTO;
import com.example.space.payloads.entities.PrenotazioneDTO;
import com.example.space.pianeti.Pianeta;
import com.example.space.user.User;
import com.example.space.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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
        LocalDate localDate = LocalDate.now();
        long p =0l;
        Pacchetto pack = new Pacchetto();
        for(Long pacchetto : prenotazioneDTO.pacchetto_id()){
            p = pacchetto;
            pack = pacchettoRepository.findById(pacchetto).get();
        }

        List<Prenotazione> prenotaziones = findByPacchettoId(p);

        if(localDate.isAfter(pack.getDa())) {
        throw new BadRequestException("Non puoi prenotare per una data già passata.");
        }

        if(prenotaziones.size()>=pack.getPosti()){
            throw new BadRequestException("Questo pacchetto ha già raggiunto il limite massimo di prenotazioni disponibili. ");
        }

            Prenotazione prenotazione = new Prenotazione();
        User user = userRepository.findById(prenotazioneDTO.user_id()).orElseThrow(()->new BadRequestException("User con id " + prenotazioneDTO.user_id() + " non trovato in db."));
prenotazione.setUser(user);
        List<Pacchetto> pacchettos= new ArrayList<>();
for(Long l : prenotazioneDTO.pacchetto_id()){
    pacchettos.add(pacchettoRepository.findById(l).orElseThrow(()-> new BadRequestException("Pacchetto con id " + l + " non trovato in db.")));
}

prenotazione.setPacchettos(pacchettos);
prenotazione.setCreated(LocalDate.now());
return prenotazioneRepository.save(prenotazione);

    }


    public  Prenotazione getById(long id){
        return  prenotazioneRepository.findById(id).orElseThrow(()->new BadRequestException("Prenotazione con id + " + id + " non trovata in db."));
    }

    public List<Prenotazione> getAll (){
        return prenotazioneRepository.findAll();
    }

    public boolean deleteById(long id){
        try {
            prenotazioneRepository.deleteById(id);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public Prenotazione putById(long id, PrenotazioneDTO prenotazioneDTO){
        Prenotazione prenotazione = prenotazioneRepository.findById(id).orElseThrow(()-> new BadRequestException("Prenotazione con id + " + id + " non trovata in db."));
        User user = userRepository.findById(prenotazioneDTO.user_id()).orElseThrow(()->new BadRequestException("User con id " + prenotazioneDTO.user_id() + " non trovato in db."));
        prenotazione.setUser(user);
        List<Pacchetto> pacchettos= new ArrayList<>();
        for(Long l : prenotazioneDTO.pacchetto_id()){
            pacchettos.add(pacchettoRepository.findById(l).orElseThrow(()-> new BadRequestException("Pacchetto con id " + l + " non trovato in db.")));
        }

        prenotazione.setPacchettos(pacchettos);
        prenotazione.setCreated(LocalDate.now());
        return prenotazioneRepository.save(prenotazione);
    }

    public List<Prenotazione> findByPacchettoId(long id){
        return  prenotazioneRepository.findByPacchettos_Id(id);
    }

    public List<Prenotazione> findByDateAndUser(LocalDate date, long user_id){
        return prenotazioneRepository.findByCreatedAndUser_Id(date,user_id);
    }

    public Page<Prenotazione> getAllPaginated(int page,int size, String orderBy){
        Pageable pageable = PageRequest.of(page,size,Sort.by(orderBy));
        return prenotazioneRepository.findAll(pageable);
    }

}
