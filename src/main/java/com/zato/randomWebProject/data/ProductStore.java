package com.zato.randomWebProject.data;

import javax.persistence.*;

@Entity
public class ProductStore {
  @Id
  @GeneratedValue
  private Long id;

  private Long quantity;

  @ManyToOne
  private Product product;

  public ProductStore() {}

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getQuantity() {
    return quantity;
  }

  public void setQuantity(Long quantity) {
    this.quantity = quantity;
  }

  public Product getProduct() {
    return product;
  }

  public void setProduct(Product product) {
    this.product = product;
  }

  @Override
  public String toString() {
    return "ProductStore{" +
        "id=" + id +
        ", quantity=" + quantity +
        ", product=" + product +
        '}';
  }
}
