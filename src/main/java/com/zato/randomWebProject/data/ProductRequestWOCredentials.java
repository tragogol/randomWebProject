package com.zato.randomWebProject.data;


public class ProductRequestWOCredentials {
  private Long id;
  private Product product;
  private Long quantity;
  private Double price;
  public String seller;

  public ProductRequestWOCredentials() {}

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

  public String getSeller() {
    return seller;
  }

  public void setSeller(String seller) {
    this.seller = seller;
  }

  public void copyValues(ProductRequest productRequest) {
    this.id = productRequest.getId();
    this.product = productRequest.getProduct();
    this.price = productRequest.getPrice();
    this.quantity = productRequest.getQuantity();
    this.seller = productRequest.seller.getUsername();
  }
}
