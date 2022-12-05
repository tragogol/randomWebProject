package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.repository.ProductRepository;
import org.springframework.stereotype.Service;

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

    repository.save(product);
    return true;
  }
}
