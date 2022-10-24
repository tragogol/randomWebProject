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

  public UserStats(Long user_id, Long health, Long damage) {
    this.user_id = user_id;
    this.health = health;
    this.damage = damage;
  }

  protected UserStats() {}

  @Override
  public String toString() {
    return "UserStats{" +
            "id=" + id +
            ", user_id=" + user_id +
            ", health=" + health +
            ", damage=" + damage +
            '}';
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getUser_id() {
    return user_id;
  }

  public void setUser_id(Long user_id) {
    this.user_id = user_id;
  }

  public Long getHealth() {
    return health;
  }

  public void setHealth(Long health) {
    this.health = health;
  }

  public Long getDamage() {
    return damage;
  }

  public void setDamage(Long damage) {
    this.damage = damage;
  }
}
