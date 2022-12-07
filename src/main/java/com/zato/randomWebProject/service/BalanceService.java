package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.Balance;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.BalanceRepository;
import com.zato.randomWebProject.repository.UsersRepository;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

  private final BalanceRepository balanceRepository;
  private final UserService userService;
  public BalanceService(BalanceRepository balanceRepository, UserService userService) {
    this.balanceRepository = balanceRepository;
    this.userService = userService;
  }

  public boolean DecreaseBalance(double value) {

    if (isDecreaseAvailable(value)){
      Users user = userService.findSelfUser();
      Balance balance = balanceRepository.findByUser(user);

      balance.setBalanceValue(balance.getBalanceValue() - value);
      return true;
    }
    else {
      return false;
    }
  }

  private boolean isDecreaseAvailable(double value) {
    Users user = userService.findSelfUser();
    Balance balance = balanceRepository.findByUser(user);

    if (balance.getBalanceValue() - value >= 0) {
      return true;
    } else {
      return false;
    }

  }

  public Balance getBalance(Users user) {
    try {
      return balanceRepository.findByUser(user);
    } catch (NullPointerException e) {
      System.out.println(e);
    }
    return null;
  }
}
