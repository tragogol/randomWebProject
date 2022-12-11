package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.Balance;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.BalanceRepository;
import com.zato.randomWebProject.repository.UsersRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import java.util.Objects;

@Service
public class BalanceService {

  @PersistenceContext
  private EntityManager em;
  private final BalanceRepository balanceRepository;
  private final UserService userService;

  public BalanceService(BalanceRepository balanceRepository, UserService userService) {
    this.balanceRepository = balanceRepository;
    this.userService = userService;
  }

  public boolean changeBalance(double value) {
    if (value < 0) {
      if (isDecreaseAvailable(value)) {
        Users user = userService.findSelfUser();

        //Balance balance = balanceRepository.findByUser(user);

        //balance.setBalanceValue(balance.getBalanceValue() - value);
        return true;
      } else {
        return false;
      }
    } else {
      Users user = userService.findSelfUser();
      //Balance balance = balanceRepository.findByUser(user);

      //balance.setBalanceValue(balance.getBalanceValue() - value);
      return true;
    }

  }

  public boolean changeBalance(double value, Users user) {


    if (value < 0) {
      if (isDecreaseAvailable(value, user)) {
        //Balance balance = balanceRepository.findByUser(user);
        //balance.setBalanceValue(balance.getBalanceValue() + value);
        return true;
      } else {
        return false;
      }
    } else {
      //Balance balance = balanceRepository.findByUser(user);
      //balance.setBalanceValue(balance.getBalanceValue() + value);
      return true;
    }
  }

  private boolean isDecreaseAvailable(double value) {
    Users user = userService.findSelfUser();
    //Balance balance = balanceRepository.findByUser(user);

//    if (balance.getBalanceValue() - value >= 0) {
//      return true;
//    } else {
//      return false;
//    }
    return true;

  }

  private boolean isDecreaseAvailable(double value, Users user) {
    //Balance balance = balanceRepository.findByUser(user);

//    if (balance.getBalanceValue() - value >= 0) {
//      return true;
//    } else {
//      return false;
//    }
    return true;

  }

  public Balance getBalance(Users user) {
    try {
      Balance userBalance = em.createQuery("SELECT u.balance FROM Users u WHERE u.id = " + user.getId(), Balance.class).getSingleResult();
      return userBalance;
    } catch (NoResultException e) {
      return null;
    }
  }

  public boolean createBalance(Users user) {
    if (getBalance(user) == null) {
      Balance balance = new Balance();
      balance.setBalanceValue(1000D);
      balanceRepository.save(balance);
      user.setBalance(balance);
      userService.updateUser(user);
      return true;
    } else {
      return false;
    }
  }




  public Balance createBalanceWithReturn(Users user) {
    Balance balance = new Balance();
    balance.setBalanceValue(1000D);
    balanceRepository.save(balance);
    user.setBalance(balance);
    userService.updateUser(user);
    return balance;
  }
}
