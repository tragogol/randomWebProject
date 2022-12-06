package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.ProductStore;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductStoreRepository extends JpaRepository<ProductStore, Long> {
  ProductStore findByUserNumberAndProductNumber(Long user, Long product);
  ProductStore findById(long id);
}
