package com.zato.randomWebProject.util;

import com.zato.randomWebProject.data.UserStats;
import com.zato.randomWebProject.repository.UserStatsRepository;

public class UserStatsChecker {

  public boolean checkNewUser(UserStats newStats) {
    if (newStats.getHealthValue() <= 0 || newStats.getDamageValue() <= 0) {
      return false;
    } else {
      return true;
    }
  }

  public boolean CheckDeadUser(UserStats userStats) {
    UserStatsRepository repository;

    if (userStats.getHealthValue() > 0) {
      return false;
    } else {
      return true;
    }
  }
}
