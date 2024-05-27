package com.example.space.prenotazioni;

import com.example.space.exceptions.BadRequestException;
import com.example.space.payloads.entities.PrenotazioneDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/prenotazioni")
public class PrenotazioneController {

@Autowired
    PrenotazioneService prenotazioneService;


@PostMapping("")
public Prenotazione save (@RequestBody @Validated PrenotazioneDTO prenotazioneDTO, BindingResult bindingResult){
    if(bindingResult.hasErrors()){
        throw new BadRequestException(bindingResult.getAllErrors());
    }
     return prenotazioneService.save(prenotazioneDTO);
}

@GetMapping("/{id}")
    public Prenotazione getById(@PathVariable long id){
    return prenotazioneService.getById(id);
}
    @GetMapping("")
    public Page<Prenotazione> getByAll(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String orderBy){
        return prenotazioneService.getAllPaginated(page,size,orderBy);
    }

}
