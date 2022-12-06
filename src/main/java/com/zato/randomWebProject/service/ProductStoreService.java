package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.ProductRequest;
import com.zato.randomWebProject.data.ProductStore;
import com.zato.randomWebProject.data.Users;
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

  public boolean isAvailable(Users user, Product product, long quantity){
    ProductStore prStore = storeRepository.findByUserAndProduct(user, product);
    if (prStore != null && prStore.getQuantity() >= quantity) {
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

  public boolean AddProduct(Product product, long quantity, Users user) {
    ProductStore tmpStore = storeRepository.findByUserAndProduct(user,product);

    if (tmpStore == null) {
      ProductStore productStore = new ProductStore();
      productStore.setQuantity(quantity);
      productStore.setProduct(product);
      productStore.setUser(user);
      try {
        storeRepository.save(productStore);
      } catch (Exception e) {
        productStore.toString();
      }
      return true;
    }
    else {
      tmpStore.setQuantity(tmpStore.getQuantity() + quantity);
      storeRepository.save(tmpStore);
      return true;
    }
  }

}
