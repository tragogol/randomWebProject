package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.UsersRepository;
import com.zato.randomWebProject.service.BalanceService;
import com.zato.randomWebProject.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/registration")
    public ResponseEntity<?> registration(Model model) {
        model.addAttribute("userForm", new Users());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body("Registration gate");
    }

    @PostMapping("/registration")
    public ResponseEntity<?> addUser(@RequestBody Users userForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something wrong");
        }

        if (!userService.saveUser(userForm)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exist");
        }

        Users tmpUser = usersRepository.findByUsername(userForm.getUsername());
        balanceService.createBalance(tmpUser);
        return ResponseEntity.status(HttpStatus.OK).body("User created");
    }
}
