package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.ProductRequest;
import com.zato.randomWebProject.data.ProductStore;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.ProductStoreRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    try {
      ProductStore prStore = storeRepository.findByUserIdAndProductId(user.getId(), product.getId());
      if (prStore.getQuantity() >= quantity) {
        return true;
      }
    } catch (NullPointerException e) {
      System.out.println(e);
    }
    return false;
  }

  public boolean isAvailable(ProductRequest productRequest){
    ProductStore prStore = storeRepository.findByUserIdAndProductId(productRequest.getSeller().getId(),
                                                                      productRequest.getProduct().getId());
    return prStore != null && prStore.getQuantity() >= productRequest.getQuantity();
  }

  public ProductStore AddProduct(Product product, long quantity, Users user) {
    ProductStore tmpStore = storeRepository.findByUserIdAndProductId(user.getId(),product.getId());

    if (tmpStore == null) {
      ProductStore productStore = new ProductStore();
      productStore.setQuantity(quantity);
      productStore.setProductId(product.getId());
      productStore.setUserId(user.getId());
      storeRepository.save(productStore);
      return productStore;
    }
    else {
      tmpStore.setQuantity(tmpStore.getQuantity() + quantity);
      storeRepository.save(tmpStore);
      return tmpStore;
    }
  }

  public boolean changeStorageQuantity(Product product, long quantity, Users user) {
    ProductStore tmpStore = storeRepository.findByUserIdAndProductId(user.getId(),product.getId());

    if (tmpStore.getQuantity() >= quantity) {
      tmpStore.setQuantity(tmpStore.getQuantity() - quantity);
      if (tmpStore.getQuantity() == 0) {
        storeRepository.delete(tmpStore);
      } else  {
        storeRepository.save(tmpStore);
      }
      return true;
    } else {
      return false;
    }
  }


  public List<ProductStore> getYourStorage(Users user) {
    return storeRepository.findByUserId(user.getId());
  }


}
