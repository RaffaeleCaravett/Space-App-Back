package com.example.space.pacchetti;

import com.example.space.exceptions.BadRequestException;
import com.example.space.payloads.entities.PacchettoDTO;
import com.example.space.pianeti.Pianeta;
import com.example.space.pianeti.PianetiRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PacchettoService {
@Autowired
    PacchettoRepository pacchettoRepository;
@Autowired
    PianetiRepository pianetiRepository;

public Pacchetto save (PacchettoDTO pacchettoDTO){
    Pacchetto pacchetto = new Pacchetto();

    pacchetto.setPosti(pacchettoDTO.posti());
pacchetto.setPrezzo(pacchettoDTO.prezzo());
pacchetto.setDa(LocalDate.of(pacchettoDTO.da().get(0),pacchettoDTO.da().get(1),pacchettoDTO.da().get(2)));
    pacchetto.setA(LocalDate.of(pacchettoDTO.a().get(0),pacchettoDTO.a().get(1),pacchettoDTO.a().get(2)));

    List<Pianeta> pianeti = new ArrayList<>();
    for (Long l : pacchettoDTO.pianeta_id()) {
        Pianeta pianeta = pianetiRepository.findById(l).orElseThrow(() -> new BadRequestException("Pianeta con id + " + l + " non trovato in db."));
        pianeti.add(pianeta);
    }

    pacchetto.setPianetas(pianeti);
    return pacchettoRepository.save(pacchetto);
}


public  Pacchetto getById(long id){
    return  pacchettoRepository.findById(id).orElseThrow(()->new BadRequestException("Pacchetto con id + " + id + " non trovato in db."));
}

public List<Pacchetto> getAll (){
    return pacchettoRepository.findAll();
}

public boolean deleteById(long id){
    try {
        pacchettoRepository.deleteById(id);
        return true;
    }catch (Exception e){
        return false;
    }
}

public Pacchetto putById(long id,PacchettoDTO pacchettoDTO){
    Pacchetto pacchetto = pacchettoRepository.findById(id).orElseThrow(()-> new BadRequestException("Pacchetto con id + " + id + " non trovato in db."));
    pacchetto.setPosti(pacchettoDTO.posti());
    pacchetto.setPrezzo(pacchettoDTO.prezzo());
    pacchetto.setDa(LocalDate.of(pacchettoDTO.da().get(0),pacchettoDTO.da().get(1),pacchettoDTO.da().get(2)));
    pacchetto.setA(LocalDate.of(pacchettoDTO.a().get(0),pacchettoDTO.a().get(1),pacchettoDTO.a().get(2)));

    List<Pianeta> pianeti = new ArrayList<>();
    for (Long l : pacchettoDTO.pianeta_id()) {
        Pianeta pianeta = pianetiRepository.findById(l).orElseThrow(() -> new BadRequestException("Pianeta con id + " + l + " non trovato in db."));
        pianeti.add(pianeta);
    }

    pacchetto.setPianetas(pianeti);
    return pacchettoRepository.save(pacchetto);
}

public List<Pacchetto> findByPianetaId(long id){
    return pacchettoRepository.findByPianetas_Id(id);
}

public List<Pacchetto> findByDates(LocalDate firstDate, LocalDate secondDate){
    return pacchettoRepository.findByDaGreaterThanEqualAndALessThanEqual(firstDate,secondDate);
}

public List<Pacchetto> findByPrezoBetween(double prezzo1,double prezzo2){
    return pacchettoRepository.findByPrezzoBetween(prezzo1,prezzo2);
}

}
