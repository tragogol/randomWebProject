package com.zato.randomWebProject.util;

import com.zato.randomWebProject.data.UserStats;

public interface IUserInteraction {
  public UserStats RecalculateStats(UserStats selfUser, UserStats targetStats);
}
