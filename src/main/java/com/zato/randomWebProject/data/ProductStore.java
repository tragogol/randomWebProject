package com.zato.randomWebProject.data;

import javax.persistence.*;

@Entity
public class ProductStore {
  @Id
  @GeneratedValue
  private Long id;

  private Long quantity;

  private Long productId;
  private Long userId;

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

  public Long getProductId() {
    return productId;
  }

  public void setProductId(Long productId) {
    this.productId = productId;
  }

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  @Override
  public String toString() {
    return "ProductStore{" +
        "id=" + id +
        ", quantity=" + quantity +
        ", productId=" + productId +
        ", userId=" + userId +
        '}';
  }
}
