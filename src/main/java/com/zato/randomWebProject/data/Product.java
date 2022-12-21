package com.zato.randomWebProject.data;

import javax.persistence.*;

@Entity
public class Product {
  @Id
  @GeneratedValue
  private Long id;
  private String name;

  public Product() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return "Product{" +
        "id=" + id +
        ", name='" + name +
        '}';
  }

}
