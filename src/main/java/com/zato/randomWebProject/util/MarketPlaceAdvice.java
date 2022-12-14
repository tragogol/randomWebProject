package com.zato.randomWebProject.util;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class MarketPlaceAdvice {

  @ResponseBody
  @ExceptionHandler(ProductRequestNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String ProductRequestNotFoundHandler(ProductRequestNotFoundException ex){ return  ex.getMessage(); }

  @ResponseBody
  @ExceptionHandler(ProductNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String ProductNotFoundHandler(ProductNotFoundException ex) { return ex.getMessage(); }

  @ResponseBody
  @ExceptionHandler(ProductStoreNotFoundException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  String ProductStoreNotFoundHandler(ProductStoreNotFoundException ex) { return ex.getMessage(); }
}
