package com.zato.randomWebProject.util;

public class UserStatsNotFoundException extends RuntimeException{
  public UserStatsNotFoundException(Long id) {
    super("Could not find userstats " + id);
  }
}
