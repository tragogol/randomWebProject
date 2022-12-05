package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.ProductStore;
import com.zato.randomWebProject.data.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStoreRepository extends JpaRepository<ProductStore, Long> {
  ProductStore findByUserAndProduct(Users user, Product product);
  ProductStore findById(long id);
}
