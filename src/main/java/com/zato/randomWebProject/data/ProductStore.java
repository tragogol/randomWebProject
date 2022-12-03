package com.zato.randomWebProject.data;

import javax.persistence.*;

@Entity
public class ProductStore {
  @Id
  @GeneratedValue
  private Long id;
  @OneToOne
  @MapsId
  private Product product;
  private Long quantity;
  @OneToOne
  @MapsId
  private Users user;

  public ProductStore() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Users getUser() {
    return user;
  }

  public void setUser(Users user) {
    this.user = user;
  }
}
