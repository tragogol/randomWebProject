package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.ProductRequest;
import com.zato.randomWebProject.data.Users;
import org.h2.engine.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRequestRepository extends JpaRepository<ProductRequest, Long> {
  ProductRequest findBySellerAndProductAndQuantityAndPrice(Users seller, Product product, Long quantity, Double price);
  List<ProductRequest> findBySeller(Users user);
  List<ProductRequest> findByProductAndPriceOrderByQuantity(Product product, Double price);
  List<ProductRequest> findByProductAndQuantityAndPrice(Product product, Long quantity, Double price);
}
