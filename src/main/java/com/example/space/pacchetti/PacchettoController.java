package com.example.space.pacchetti;

import com.example.space.exceptions.BadRequestException;
import com.example.space.payloads.entities.PacchettoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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


    @GetMapping("/byParametes")
    public List<Pacchetto> getByParameters(@RequestParam(defaultValue = "0") long id, @RequestParam(defaultValue = "0") double prezzo, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}")LocalDate date1,
                                           @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}")LocalDate date2){
        List <Pacchetto> pacchettos = new ArrayList<>();
        if(id!=0&&prezzo==0&& Objects.equals(date1, LocalDate.now()) && Objects.equals(date2, LocalDate.now())){
            Pacchetto pacchetto = pacchettoService.getById(id);
            pacchettos.add(pacchetto);
            return pacchettos;
        }else if (id==0&&prezzo!=0&& Objects.equals(date1, LocalDate.now()) && Objects.equals(date2, LocalDate.now())){
            pacchettos.addAll(pacchettoService.findByPrezoBetween(0,prezzo));
        }else if(id==0&&prezzo==0&& !Objects.equals(date1, LocalDate.now()) && !Objects.equals(date2, LocalDate.now())){
            pacchettos.addAll(pacchettoService.findByDates(date1,date2));
        }else if(id!=0&&prezzo!=0&& Objects.equals(date1, LocalDate.now()) && Objects.equals(date2, LocalDate.now())){
            pacchettos.addAll(pacchettoService.getByIdAndPrezzo(id,prezzo));
        }else if(id!=0&&prezzo!=0&& !Objects.equals(date1, LocalDate.now()) && !Objects.equals(date2, LocalDate.now())){
            pacchettos.addAll(pacchettoService.getByIdAndPrezzoAndDates(id,prezzo,date1,date2));
        }else if(id==0&&prezzo!=0&& !Objects.equals(date1, LocalDate.now()) && !Objects.equals(date2, LocalDate.now())){
            pacchettos.addAll(pacchettoService.getByPrezzoAndDates(prezzo,date1,date2));
        }else{
            throw new BadRequestException("Qualcosa non va nell'elaborazione della richiesta.");
        }
        if(!pacchettos.isEmpty()){
            return pacchettos;
        }else{
            throw new BadRequestException("Non ci sono pacchetti con i requisiti giusti in database.");
        }
    }
}
