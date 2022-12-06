package com.zato.randomWebProject.data;

import javax.persistence.*;

@Entity
public class ProductStore {
  @Id
  @GeneratedValue
  private Long id;

  private Long quantity;

  private Long productNumber;
  private Long userNumber;

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

  public Long getProductNumber() {
    return productNumber;
  }

  public void setProductNumber(Long productNumber) {
    this.productNumber = productNumber;
  }

  public Long getUserNumber() {
    return userNumber;
  }

  public void setUserNumber(Long userNumber) {
    this.userNumber = userNumber;
  }
}
