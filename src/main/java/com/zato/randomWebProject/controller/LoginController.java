package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.Users;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class LoginController {

    private final AuthenticationManager authentication;

    public LoginController(AuthenticationManager authentication) {
        this.authentication = authentication;
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth.getName() != null) {
            return ResponseEntity.status(HttpStatus.OK).body("You are " + auth.getName());
        } else  {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Not logged in");
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) throws Exception {
        Authentication authObj;
        try {
            authObj = authentication.authenticate(new
                    UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authObj);
        } catch (BadCredentialsException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Credential invalid");
        }
        return ResponseEntity.status(HttpStatus.OK).body("Logged");
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return ResponseEntity.status(HttpStatus.OK).body("Logout");
    }

}
