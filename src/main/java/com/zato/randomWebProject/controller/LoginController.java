package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.util.UserAlreadyLoginException;
import com.zato.randomWebProject.util.UserNotAuthenticatedException;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class LoginController {

    private final AuthenticationManager authentication;

    public LoginController(AuthenticationManager authentication) {
        this.authentication = authentication;
    }

    @GetMapping("/login")
    public ResponseEntity<?> loginGet() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getPrincipal() == "anonymousUser") {
            throw new UserNotAuthenticatedException();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body("Login as " + auth.getName());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Users user) {
        Authentication authObj;
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth.getPrincipal() == "anonymousUser") {
                authObj = authentication.authenticate(new
                    UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
                SecurityContextHolder.getContext().setAuthentication(authObj);
                return ResponseEntity.status(HttpStatus.OK).body("Successful login, " + authObj.getName());
            } else {
                throw new UserAlreadyLoginException(auth.getName());
            }
        } catch (BadCredentialsException ex){
                throw new BadCredentialsException("Bad credentials");
        }
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
