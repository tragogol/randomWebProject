package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.Balance;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.BalanceRepository;
import com.zato.randomWebProject.service.BalanceService;
import com.zato.randomWebProject.service.UserService;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.security.auth.login.LoginException;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class BalanceController {

  private final BalanceService balanceService;
  private final BalanceRepository balanceRepository;
  private final UserService userService;

  public BalanceController(BalanceService balanceService, BalanceRepository balanceRepository, UserService userService) {
    this.balanceService = balanceService;
    this.balanceRepository = balanceRepository;
    this.userService = userService;
  }

  @GetMapping("/balance/create_balance")
  public ResponseEntity<?> createBalance(){
    Users user = userService.findSelfUser();
    if (balanceService.getBalance(user) == null) {
      balanceService.createBalance(user);
      return ResponseEntity.status(HttpStatus.OK).body("Balance created");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You already have one");
    }
  }

  @GetMapping("/balance")
  public EntityModel<Balance> getBalance() throws LoginException {
    try {
      Balance tmpBalance = balanceService.getBalance(userService.findSelfUser());
      return EntityModel.of(tmpBalance,
              linkTo(methodOn(BalanceController.class).getBalance()).withSelfRel());
    } catch (IllegalArgumentException tmpBalance) {
      throw new LoginException();
    }
  }
}
