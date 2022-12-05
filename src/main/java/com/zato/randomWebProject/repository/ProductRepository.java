package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  Product findById(long id);
  Product findByName(String name);

}
