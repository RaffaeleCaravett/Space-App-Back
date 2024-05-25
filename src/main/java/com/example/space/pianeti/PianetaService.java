package com.example.space.pianeti;

import com.example.space.enums.Galassia;
import com.example.space.exceptions.NotFoundException;
import com.example.space.payloads.entities.PianetaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PianetaService {
@Autowired
    PianetiRepository pianetiRepository;

public Pianeta save(PianetaDTO pianetaDTO){
    Pianeta pianeta = new Pianeta();
    pianeta.setNome(pianetaDTO.nome());
    pianeta.setGalassia(Galassia.valueOf(pianetaDTO.galassia()));
    return pianetiRepository.save(pianeta);
}
public Pianeta getById(long id){
    return pianetiRepository.findById(id).orElseThrow(()-> new NotFoundException("Pianeta con id " + id + " non trovato in db."));
}
    public boolean deleteById(long id){
    try {
        pianetiRepository.deleteById(id);
    return true;
    }catch (Exception e){
        return false;
    }
    }

    public Pianeta putById(long id , PianetaDTO pianetaDTO){
    Pianeta pianeta = pianetiRepository.findById(id).orElseThrow(()-> new NotFoundException("Pianeta con id " + id + " non trovato in db."));
    pianeta.setNome(pianetaDTO.nome());
pianeta.setGalassia(Galassia.valueOf(pianetaDTO.galassia()));
return pianetiRepository.save(pianeta);
}

public List<Pianeta> getAll(){
    return pianetiRepository.findAll();
}


}
