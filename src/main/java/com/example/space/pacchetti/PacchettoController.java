package com.example.space.pacchetti;

import com.example.space.exceptions.BadRequestException;
import com.example.space.payloads.entities.PacchettoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pacchetto")
public class PacchettoController {
    @Autowired
    PacchettoService pacchettoService;


    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Pacchetto save(@RequestBody @Validated PacchettoDTO pacchettoDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return pacchettoService.save(pacchettoDTO);
    }
    @GetMapping("")
    public List<Pacchetto> getAll(){
        return pacchettoService.getAll();
    }
    @GetMapping("/{id}")
    public Pacchetto getById(@PathVariable long id){
        return pacchettoService.getById(id);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Pacchetto putById(@PathVariable long id,@RequestBody @Validated PacchettoDTO pacchettoDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return pacchettoService.putById(id,pacchettoDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public boolean deleteById(@PathVariable long id){
        return pacchettoService.deleteById(id);
    }


    @GetMapping("/prezzo/{prezzoUno}/{prezzoDue}")
    public List<Pacchetto> getByPrezzoBetween(@PathVariable double prezzoUno , @PathVariable double prezzoDue){
        return pacchettoService.findByPrezoBetween(prezzoUno,prezzoDue);
    }

    @GetMapping("/date")
    public List<Pacchetto> getByDateBetween(@RequestParam int annoUno,@RequestParam int meseUno ,@RequestParam int giornoUno,
                                            @RequestParam int annoDue,@RequestParam int meseDue ,@RequestParam int giornoDue){
        return pacchettoService.findByDates(LocalDate.of(annoUno,meseUno,giornoUno),LocalDate.of(annoDue,meseDue,giornoDue));
    }
    @GetMapping("/pianeta/{pianetaId}")
    public List<Pacchetto> getByDateBetween(@PathVariable long pianetaId){
        return pacchettoService.findByPianetaId(pianetaId);
    }
}
