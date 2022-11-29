package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.repository.UsersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<?> knowRole(@PathVariable String username) {
        return ResponseEntity.status(HttpStatus.OK).body("you are " + repository.findByUsername(username).toString());
    }
}
