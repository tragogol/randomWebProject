package com.zato.randomWebProject.data;

import com.sun.istack.NotNull;
import com.zato.randomWebProject.util.Roles;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Users {
  private @Id @GeneratedValue Long user_id;
  @NotNull
  private String username;
  @NotNull
  private String password;
  @NotNull
  private Roles Role;
}
