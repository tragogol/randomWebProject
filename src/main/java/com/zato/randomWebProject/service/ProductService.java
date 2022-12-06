package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
  private final ProductRepository repository;

  public ProductService(ProductRepository repository) {
    this.repository = repository;
  }

  public boolean createNewProduct(Product product) {
    if (repository.findByName(product.getName()) != null) {
      return false;
    }
    Product tmpProduct = new Product();
    tmpProduct.setName(product.getName());
    repository.save(tmpProduct);
    return true;
  }

  public Product createProductWithReturn(Product product) {
    if (repository.findByName(product.getName()) != null) {
      return null ;
    }
    Product tmpProduct = new Product();
    tmpProduct.setName(product.getName());
    repository.save(tmpProduct);
    return tmpProduct;
  }

  public List<Product> getAllProduct() {
    List<Product> tmpList = null;
    tmpList = repository.findAll();
    return tmpList;
  }

}
