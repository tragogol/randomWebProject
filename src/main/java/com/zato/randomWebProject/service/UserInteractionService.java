package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.UserStats;
import com.zato.randomWebProject.repository.UserStatsRepository;
import com.zato.randomWebProject.util.UserStatsChecker;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class UserInteractionService {

  public static void ChangeHealthValues(UserStats firstUser, UserStats secondUser, UserStatsChecker userChecker, UserStatsRepository repository) {
    secondUser.setHealthValue(secondUser.getHealthValue() - firstUser.getDamageValue());
    firstUser.setHealthValue(firstUser.getHealthValue() - secondUser.getDamageValue());

    if (userChecker.CheckDeadUser(secondUser)){
      repository.delete(secondUser);
      firstUser.setHealthValue(firstUser.getHealthValue() + 100);
    } else {
      repository.save(secondUser);
    }
    if (userChecker.CheckDeadUser(firstUser)) {
      repository.delete(firstUser);
      secondUser.setHealthValue(secondUser.getHealthValue() + 100);

    } else {
      repository.save(firstUser);
    }
  }
}
