package com.zato.randomWebProject.data;

import javax.persistence.*;

@Entity
public class ProductStore {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long store_id;

  private Long quantity;

  @ManyToOne
  @MapsId
  private Product product;
  @ManyToOne
  @MapsId
  private Users user;

  public ProductStore() {}

  public Long getStore_id() {
    return store_id;
  }

  public void setStore_id(Long id) {
    this.store_id = id;
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
