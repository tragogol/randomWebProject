package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.*;
import com.zato.randomWebProject.repository.ProductRequestRepository;
import com.zato.randomWebProject.repository.ProductStoreRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.Objects;

@Service
public class ProductRequestService {
  @PersistenceContext
  private EntityManager em;
  private final ProductRequestRepository productRequestRepository;

  public ProductRequestService(ProductRequestRepository productRequestRepository) {
    this.productRequestRepository = productRequestRepository;
  }

  public boolean ReplaceRequest(ProductRequest request) {
    productRequestRepository.save(request);
    return true;
  }

  public ProductRequest createProductRequest(Product product, Users seller, long quantity, double price) {
    ProductRequest productRequest = new ProductRequest();
    productRequest.setProduct(product);
    productRequest.setSeller(seller);
    productRequest.setQuantity(quantity);
    productRequest.setPrice(price);
    return productRequest;
  }

  public double getProductPrice(Product product) {

    Double minPrice = em.createQuery("SELECT Min(pr.price) FROM ProductRequest pr WHERE pr.product.id = " + product.getId(), Double.class).getSingleResult();
    return Objects.requireNonNullElse(minPrice, 0D);

  }

  public double getProductPrice(long id) {

    Double minPrice = em.createQuery("SELECT Min(pr.price) FROM ProductRequest pr WHERE pr.product.id = " + id, Double.class).getSingleResult();
    return Objects.requireNonNullElse(minPrice, 0D);
  }

  public List<ProductRequest> getRequestList() {
    return em.createQuery("SELECT pr FROM ProductRequest pr", ProductRequest.class).getResultList();
  }

  public List<ProductRequest> getRequestListByProduct(Product product) {
    return em.createQuery("SELECT pr FROM ProductRequest pr WHERE pr.product = " + product.getId(), ProductRequest.class).getResultList();
  }


  public boolean buyProduct(Balance balance, ProductStore userStorage, Product product, Long quantity, Double price) {
    List<ProductRequest> tmpProductList = productRequestRepository.findByProductAndPriceOrderByQuantity(product, price);
    Double tmpBalanceValue = balance.getBalanceValue();
    for (ProductRequest singleRequest :
        tmpProductList) {
      if (singleRequest.getQuantity() > quantity) {

      }
    }

    clearRequests();
    return true;
  }

  public boolean isAvailableRequest(Product product, long quantity, double minPrice) {
    List<ProductRequest> tmpProductList = getRequestListByProduct(product);
    if(tmpProductList.isEmpty()) return false;

    long tmpQuantity = 0;
    for (ProductRequest singleRequest :
        tmpProductList) {
      if(singleRequest.getPrice() <= minPrice) {
        tmpQuantity += singleRequest.getQuantity();
      }
      if (tmpQuantity >= quantity) return true;
    }

    return false;
  }

  private void clearRequests() {
    List<ProductRequest> requestsList = productRequestRepository.findAll();
    for (ProductRequest singleRequest :
        requestsList) {
      if (singleRequest.getQuantity() == 0) {
        productRequestRepository.delete(singleRequest);
      }
    }
  }


}
