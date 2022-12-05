package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.ProductRequest;
import com.zato.randomWebProject.data.ProductStore;
import com.zato.randomWebProject.repository.ProductRepository;
import com.zato.randomWebProject.repository.ProductStoreRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class ProductStoreService {

  @PersistenceContext
  private EntityManager em;
  private final ProductStoreRepository storeRepository;

  public ProductStoreService(EntityManager em, ProductStoreRepository storeRepository) {
    this.em = em;
    this.storeRepository = storeRepository;
  }

  public boolean isAvailable(int userId, int count){
    ProductStore prStore = storeRepository.findById(userId);
    if (prStore != null && prStore.getQuantity() >= count) {
      return true;
    } else {
      return false;
    }
  }

  public boolean isAvailable(ProductRequest productRequest){
    ProductStore prStore = storeRepository.findByUserAndProduct(productRequest.getSeller(), productRequest.getProduct());
    if (prStore != null && prStore.getQuantity() >= productRequest.getQuantity()) {
      return true;
    } else {
      return false;
    }
  }

}
