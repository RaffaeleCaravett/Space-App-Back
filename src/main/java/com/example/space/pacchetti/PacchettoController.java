package com.example.space.pacchetti;

import com.example.space.exceptions.BadRequestException;
import com.example.space.payloads.entities.PacchettoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public Page<Pacchetto> getByPrezzoBetween(@PathVariable double prezzoUno , @PathVariable double prezzoDue){
        Pageable pageable = PageRequest.of(0,5,Sort.by("id"));
        return pacchettoService.findByPrezoBetween(prezzoUno,prezzoDue,pageable);
    }

    @GetMapping("/date")
    public Page<Pacchetto> getByDateBetween(@RequestParam int annoUno,@RequestParam int meseUno ,@RequestParam int giornoUno,
                                            @RequestParam int annoDue,@RequestParam int meseDue ,@RequestParam int giornoDue){
        Pageable pageable = PageRequest.of(0,5,Sort.by("id"));
        return pacchettoService.findByDates(LocalDate.of(annoUno,meseUno,giornoUno),LocalDate.of(annoDue,meseDue,giornoDue),pageable);
    }
    @GetMapping("/pianeta/{pianetaId}")
    public List<Pacchetto> getByDateBetween(@PathVariable long pianetaId){
        return pacchettoService.findByPianetaId(pianetaId);
    }


    @GetMapping("/byParametes")
    public Page<Pacchetto> getByParameters(@RequestParam(defaultValue = "0") long id, @RequestParam(defaultValue = "0") double prezzo, @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}")LocalDate date1,
                                           @RequestParam(defaultValue = "#{T(java.time.LocalDate).now()}")LocalDate date2,@RequestParam(defaultValue = "0") int page,@RequestParam(defaultValue = "5") int size){
        Pageable pageable = PageRequest.of(page,size, Sort.by("id"));
        if(id!=0&&prezzo==0&& Objects.equals(date1, LocalDate.now()) && Objects.equals(date2, LocalDate.now())){
            return  pacchettoService.getByIdPaginated(id);
        }else if (id==0&&prezzo!=0&& Objects.equals(date1, LocalDate.now()) && Objects.equals(date2, LocalDate.now())){
            return pacchettoService.findByPrezoBetween(0,prezzo,pageable);
        }else if(id==0&&prezzo==0&& !Objects.equals(date1, LocalDate.now()) && !Objects.equals(date2, LocalDate.now())){
            return pacchettoService.findByDates(date1,date2,pageable);
        }else if(id!=0&&prezzo!=0&& Objects.equals(date1, LocalDate.now()) && Objects.equals(date2, LocalDate.now())){
            return pacchettoService.getByIdAndPrezzo(id,prezzo,pageable);
        }else if(id!=0&&prezzo!=0&& !Objects.equals(date1, LocalDate.now()) && !Objects.equals(date2, LocalDate.now())){
            return pacchettoService.getByIdAndPrezzoAndDates(id,prezzo,date1,date2,pageable);
        }else if(id==0&&prezzo!=0&& !Objects.equals(date1, LocalDate.now()) && !Objects.equals(date2, LocalDate.now())){
            return pacchettoService.getByPrezzoAndDates(prezzo,date1,date2,pageable);
        }else{
            throw new BadRequestException("Qualcosa non va nell'elaborazione della richiesta.");
        }
    }
}
