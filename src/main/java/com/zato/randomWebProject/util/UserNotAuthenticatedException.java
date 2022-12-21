package com.zato.randomWebProject.util;

public class UserNotAuthenticatedException extends  RuntimeException{
  public UserNotAuthenticatedException() {
    super("You are not authenticated");
  }
}
