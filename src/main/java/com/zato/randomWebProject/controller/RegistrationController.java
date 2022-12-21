package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.UsersRepository;
import com.zato.randomWebProject.service.BalanceService;
import com.zato.randomWebProject.service.UserService;
import com.zato.randomWebProject.util.UserAlreadyExistException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class RegistrationController {

    private final UsersRepository usersRepository;
    private final UserService userService;
    private final BalanceService balanceService;

    public RegistrationController(UsersRepository usersRepository, UserService userService, BalanceService balanceService) {
        this.usersRepository = usersRepository;
        this.userService = userService;
        this.balanceService = balanceService;
    }

    @PostMapping("/registration")
    public ResponseEntity<?> addUser(@RequestBody Users userForm) {
        if (userService.saveUser(userForm)){
            Users tmpUser = usersRepository.findByUsername(userForm.getUsername());
            balanceService.createBalance(tmpUser);
            return ResponseEntity.status(HttpStatus.OK).body("Successful registration");
        } else {
            throw new UserAlreadyExistException() ;
        }

    }
}
