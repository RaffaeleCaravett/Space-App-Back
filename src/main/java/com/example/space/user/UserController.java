package com.example.space.user;

import com.example.space.exceptions.BadRequestException;
import com.example.space.payloads.entities.Passwords;
import com.example.space.payloads.entities.UserRegistrationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/{id}")
    @PreAuthorize("HasAnyAuthority('USER','ADMIN')")
    public User getById(@PathVariable long id){
        return userService.getById(id);
    }
    @GetMapping("")
    public List<User> getAll(){
        return userService.getAll();
    }

    @PutMapping("/{id}")
    @PreAuthorize("HasAuthority('ADMIN')")
    public User putById(@PathVariable long id, @RequestBody @Validated UserRegistrationDTO userRegistrationDTO , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.putById(id,userRegistrationDTO);
    }
    @PutMapping("/me")
    @PreAuthorize("HasAuthority('USER')")
    public User putMeById(@AuthenticationPrincipal User currentUser, @RequestBody @Validated UserRegistrationDTO userRegistrationDTO , BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new BadRequestException(bindingResult.getAllErrors());
        }
        return userService.putById(currentUser.getId(),userRegistrationDTO);
    }

    @DeleteMapping("/me")
    @PreAuthorize("HasAuthority('USER')")
    public boolean deleteMeById(@AuthenticationPrincipal User user){
        return userService.deleteById(user.getId());
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("HasAuthority('ADMIN')")
    public boolean deleteById(@PathVariable long id){
        return userService.deleteById(id);
    }

    @GetMapping("/email/{email}")
    @PreAuthorize("HasAnyAuthority('USER','ADMIN')")
  public List<User> findByEmail(@PathVariable String email){
        return userService.findByEmail(email);
    }
    @PutMapping("/password/me")
    @PreAuthorize("HasAnyAuthority('USER','ADMIN')")
    public boolean findByEmail(@AuthenticationPrincipal User user,@RequestBody Passwords passwords){
        return userService.changePassword(user.getId(),passwords.oldPass(),passwords.newPass());
    }


}
