package com.zato.randomWebProject.data;

import javax.persistence.*;

@Entity
public class ProductRequest {
  @Id
  @GeneratedValue
  private Long id;
  @ManyToOne
  private Product product;

  private Long quantity;
  private Double price;

  @ManyToOne
  @JoinColumn(name="userId", nullable=false, updatable=false)
  public Users seller;
  public ProductRequest() {}

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

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public Users getSeller() {
    return seller;
  }

  public void setSeller(Users seller) {
    this.seller = seller;
  }

  @Override
  public String toString() {
    return "ProductRequest{" +
        "id=" + id +
        ", product=" + product +
        ", quantity=" + quantity +
        ", price=" + price +
        ", seller=" + seller +
        '}';
  }
}
