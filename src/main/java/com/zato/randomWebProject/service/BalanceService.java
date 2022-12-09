package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.Balance;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.BalanceRepository;
import org.springframework.stereotype.Service;

@Service
public class BalanceService {

  private final BalanceRepository balanceRepository;
  private final UserService userService;
  public BalanceService(BalanceRepository balanceRepository, UserService userService) {
    this.balanceRepository = balanceRepository;
    this.userService = userService;
  }

  public boolean ChangeBalance(double value) {
    if (value < 0) {
      if (isDecreaseAvailable(value)){
        Users user = userService.findSelfUser();
        Balance balance = balanceRepository.findByUser(user);

        balance.setBalanceValue(balance.getBalanceValue() - value);
        return true;
      }
      else {
        return false;
      }
    } else {
      Users user = userService.findSelfUser();
      Balance balance = balanceRepository.findByUser(user);

      balance.setBalanceValue(balance.getBalanceValue() - value);
      return true;
    }

  }

  public boolean ChangeBalance(double value, Users user) {
    if (value < 0) {
      if (isDecreaseAvailable(value, user)){
        Balance balance = balanceRepository.findByUser(user);
        balance.setBalanceValue(balance.getBalanceValue() + value);
        return true;
      }
      else {
        return false;
      }
    } else {
      Balance balance = balanceRepository.findByUser(user);
      balance.setBalanceValue(balance.getBalanceValue() + value);
      return true;
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

  private boolean isDecreaseAvailable(double value, Users user) {
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

  public boolean createBalance(Users user) {
    Balance balance = new Balance();
    balance.setUser(user);
    balance.setBalanceValue(1000D);
    balanceRepository.save(balance);
    return true;
  }
}
