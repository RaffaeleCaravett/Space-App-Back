package com.example.space.pianeti;

import com.example.space.enums.Galassia;
import com.example.space.exceptions.BadRequestException;
import com.example.space.exceptions.NotFoundException;
import com.example.space.payloads.entities.PianetaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PianetaService {
@Autowired
    PianetiRepository pianetiRepository;

public Pianeta save(PianetaDTO pianetaDTO) {
    Pianeta pianeta = new Pianeta();
if(findByNomeAndGalassia(pianetaDTO.nome(),Galassia.valueOf(pianetaDTO.galassia()))) {
    pianeta.setNome(pianetaDTO.nome());
    pianeta.setGalassia(Galassia.valueOf(pianetaDTO.galassia()));
    return pianetiRepository.save(pianeta);
}else{
    throw new BadRequestException("Pianeta con questi parametri già presente in DB.");
}
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

public Page<Pianeta> getAllPaginated(int page) {
    Pageable pageable = PageRequest.of(page,10, Sort.by("id"));
    return  pianetiRepository.findAll(pageable);
}

public boolean findByNomeAndGalassia(String nome, Enum galassia){
    List<Pianeta> pianeta = pianetiRepository.findByNomeAndGalassia(nome,galassia);
    if(pianeta.isEmpty()){
        return  true;
    }else{
        return false;
    }
}

public Pianeta findByParameters(long id,String nome,Enum galassia){

    if(pianetiRepository.findByIdAndNomeAndGalassia(id,nome,galassia).isPresent()){
        return pianetiRepository.findByIdAndNomeAndGalassia(id,nome,galassia).get();
    }else{
        throw new BadRequestException("Non ci sono pianeti con questi parametri in db");
    }

}
}
