package com.zato.randomWebProject.util;

import com.zato.randomWebProject.data.Users;

public class UserAlreadyLoginException extends RuntimeException{
  public UserAlreadyLoginException(String username) {
    super("Already login. you are " + username);
  }
}
