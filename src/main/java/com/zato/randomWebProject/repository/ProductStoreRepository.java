package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.ProductStore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductStoreRepository extends JpaRepository<ProductStore, Long> {
  ProductStore findByUserIdAndProductId(Long user, Long product);
  List<ProductStore> findByUserId(Long userId);

  List<ProductStore> findAllByUserId(Long userId);
}
