package com.zato.randomWebProject.util;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class UserAdvice {

  @ResponseBody
  @ExceptionHandler(UserAlreadyExistException.class)
  @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
  String UserAlreadyExistHandler(UserAlreadyExistException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(UserAlreadyLoginException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String UserAlreadyLoginHandler(UserAlreadyLoginException ex) {
    return ex.getMessage();
  }

  @ResponseBody
  @ExceptionHandler(BadCredentialsException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String BadCredentialHandler(BadCredentialsException ex) {return ex.getMessage(); }

  @ResponseBody
  @ExceptionHandler(UserNotAuthenticatedException.class)
  @ResponseStatus(HttpStatus.UNAUTHORIZED)
  String UserNotAuthneticatedHandler(UserNotAuthenticatedException ex) {return ex.getMessage(); }
}
