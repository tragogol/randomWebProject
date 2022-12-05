package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContentExampleController {

    private final UsersRepository repository;

    public ContentExampleController(UsersRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/contentPage")
    public ResponseEntity<?> CheckAccess() {
        return ResponseEntity.status(HttpStatus.OK).body("You have access");
    }

    @GetMapping("/getRole/{username}")
    public ResponseEntity<?> getUser(@PathVariable String username) {
        try {
            return ResponseEntity.status(HttpStatus.OK).body("you are " + repository.findByUsername(username).toString());
        } catch (NullPointerException e) {
            return ResponseEntity.status(HttpStatus.OK).body("there is no " + username);
        }
    }
}
