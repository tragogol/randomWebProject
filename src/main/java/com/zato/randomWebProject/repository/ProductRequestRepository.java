package com.zato.randomWebProject.repository;

import com.zato.randomWebProject.data.ProductRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRequestRepository extends JpaRepository<ProductRequest, Long> {
}
