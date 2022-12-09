package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.BalanceRepository;
import com.zato.randomWebProject.service.BalanceService;
import com.zato.randomWebProject.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BalanceController {

  private final BalanceService balanceService;
  private final BalanceRepository balanceRepository;
  private UserService userService;

  public BalanceController(BalanceService balanceService, BalanceRepository balanceRepository, UserService userService) {
    this.balanceService = balanceService;
    this.balanceRepository = balanceRepository;
    this.userService = userService;
  }

  @PostMapping("/balance/create_balance")
  public ResponseEntity<?> createBalance(){
    Users user = userService.findSelfUser();
    if (balanceService.getBalance(user) == null) {
      balanceService.createBalance(user);
      return ResponseEntity.status(HttpStatus.OK).body("Balance created");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("You already have one");
    }
  }
}
