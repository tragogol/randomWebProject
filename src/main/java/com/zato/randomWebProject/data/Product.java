package com.zato.randomWebProject.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Product {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long product_id;
  private String name;

  public Product() {}

  public Long getProduct_id() {
    return product_id;
  }

  public void setProduct_id(Long id) {
    this.product_id = id;
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
        "id=" + product_id +
        ", name='" + name +
        '}';
  }
}
