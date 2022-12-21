package com.zato.randomWebProject.util;

import com.zato.randomWebProject.data.Users;

public class UserAlreadyExistException extends RuntimeException {
  public UserAlreadyExistException() {
    super("User with this data already exist");
  }
}
