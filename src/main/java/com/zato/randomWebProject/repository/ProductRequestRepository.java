package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.ProductRequest;
import com.zato.randomWebProject.data.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRequestRepository extends JpaRepository<ProductRequest, Long> {
  ProductRequest findBySellerAndProductAndQuantityAndPrice(Users seller, Product product, Long quantity, Double price);
  List<ProductRequest> findBySeller(Users user);
  List<ProductRequest> findByProductAndPriceOrderByQuantity(Product product, Double price);
  List<ProductRequest> findByProductAndQuantityAndPrice(Product product, Long quantity, Double price);
  Optional<ProductRequest> findById(Long id);
}
