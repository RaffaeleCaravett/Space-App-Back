package com.example.space.pianeti;

import com.example.space.exceptions.BadRequestException;
import com.example.space.payloads.entities.PianetaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pianeti")
public class PianetaController {
    @Autowired
    PianetaService pianetaService;

    @PostMapping("")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Pianeta save(@RequestBody @Validated PianetaDTO pianetaDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return pianetaService.save(pianetaDTO);
    }

    @GetMapping("")
    public List<Pianeta> getAll(){
        return pianetaService.getAll();
    }
    @GetMapping("/paginated")
    public Page<Pianeta> getAllPaginated(){
        return pianetaService.getAllPaginated();
    }
    @GetMapping("/{id}")
    public Pianeta getById (@PathVariable long id){
        return pianetaService.getById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('Admin')")
    public Pianeta putById(@PathVariable long id,@RequestBody @Validated PianetaDTO pianetaDTO,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return pianetaService.putById(id,pianetaDTO);
    }
@DeleteMapping("/{id}")
@PreAuthorize("hasAuthority('Admin')")
    public boolean deleteById(@PathVariable long id){
        return pianetaService.deleteById(id);
}

}
