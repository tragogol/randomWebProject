package com.zato.randomWebProject.data;

import javax.persistence.*;

@Entity
@Table(name="usr_stats")
public class UserStats {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;
  private Long user_id;
  private Long health;
  private Long damage;
}
