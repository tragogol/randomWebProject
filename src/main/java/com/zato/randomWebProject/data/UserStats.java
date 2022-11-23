package com.zato.randomWebProject.data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class UserStats {
  private @Id @GeneratedValue Long id;
  private Integer healthValue;
  private Integer damageValue;

  public UserStats() {}

  public UserStats(int healthValue, int damageValue) {
    this.healthValue = healthValue;
    this.damageValue = damageValue;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Integer getHealthValue() {
    return healthValue;
  }

  public void setHealthValue(int healthValue) {
    this.healthValue = healthValue;
  }

  public Integer getDamageValue() {
    return damageValue;
  }

  public void setDamageValue(int damageValue) {
    this.damageValue = damageValue;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof UserStats userStats)) return false;
    return Objects.equals(healthValue, userStats.healthValue) && Objects.equals(damageValue, userStats.damageValue) && Objects.equals(id, userStats.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, healthValue, damageValue);
  }

  @Override
  public String toString() {
    return "UserStats{" +
        "id=" + id +
        ", healthValue=" + healthValue +
        ", damageValue=" + damageValue +
        '}';
  }
}
