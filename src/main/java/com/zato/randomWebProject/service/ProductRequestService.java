package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.*;
import com.zato.randomWebProject.repository.ProductRequestRepository;
import com.zato.randomWebProject.repository.ProductStoreRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class ProductRequestService {
  @PersistenceContext
  private EntityManager em;
  private final ProductRequestRepository productRequestRepository;
  private final ProductStoreRepository productStoreRepository;
  private final BalanceService balanceService;

  public ProductRequestService(ProductRequestRepository productRequestRepository, ProductStoreRepository productStoreRepository, BalanceService balanceService) {
    this.productRequestRepository = productRequestRepository;
    this.productStoreRepository = productStoreRepository;
    this.balanceService = balanceService;
  }

  public boolean createProductRequest(Product product, Users seller, long quantity, double price) {
    ProductRequest productRequest = new ProductRequest();
    productRequest.setProduct(product);
    productRequest.setSeller(seller);
    productRequest.setQuantity(quantity);
    productRequest.setPrice(price);

    productRequestRepository.save(productRequest);
    return true;
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

  public List<ProductRequest> getRequestListByProductAndPrice(Product product, Double maxPrice) {
    return em.createQuery("SELECT pr " +
        "FROM ProductRequest pr " +
        "WHERE pr.product = " + product.getId() + " AND pr.price <= " + maxPrice + " " +
        "ORDER BY pr.quantity", ProductRequest.class).getResultList();
  }


  public boolean buyProduct(Balance balance, Users user ,ProductStore userStorage, Product product, Long quantity, Double price) {
    List<ProductRequest> tmpProductList = getRequestListByProductAndPrice(product, price);
    Long restQuantity = quantity;
    double finalPrice = 0D;
    List<ProductRequest> requestsToBuy = new ArrayList<>();

    for (ProductRequest productRequest : tmpProductList) {
      if (restQuantity - productRequest.getQuantity() >= 0) {
        finalPrice += productRequest.getQuantity() * productRequest.getPrice();
        restQuantity -= productRequest.getQuantity();
        requestsToBuy.add(productRequest);
      } else {
        finalPrice += productRequest.getPrice() * restQuantity;
        restQuantity = 0L;
      }
      if (restQuantity <= 0) break;
    }

    if (restQuantity > 0 || finalPrice > balance.getBalanceValue()) return false;

    restQuantity = quantity;
    for (ProductRequest productRequest : requestsToBuy) {
      if ( productRequest.getQuantity() < restQuantity) {
        double currentPrice = productRequest.getQuantity() * productRequest.getPrice();
        finalPrice += currentPrice;
        balanceService.ChangeBalance(currentPrice, productRequest.seller);
        restQuantity -= productRequest.getQuantity();
        productRequest.setQuantity(0L);
      } else {
        double currentPrice = productRequest.getPrice() * restQuantity;
        finalPrice += currentPrice;
        balanceService.ChangeBalance(currentPrice, productRequest.seller);
        productRequest.setQuantity(productRequest.getQuantity() - restQuantity);
      }

      if (restQuantity == 0) break;
    }

    checkRequest(requestsToBuy);
    userStorage.setQuantity(quantity);
    balanceService.ChangeBalance(-finalPrice, user);
    return true;
  }

  private void checkRequest(ProductRequest productRequest) {
    if (productRequest.getQuantity() == 0) {
      productRequestRepository.delete(productRequest);
    } else {
      productRequestRepository.save(productRequest);
    }
  }

  private void checkRequest(List<ProductRequest> productRequests) {
    for (ProductRequest productRequest : productRequests) {
      if (productRequest.getQuantity() == 0) {
        productRequestRepository.delete(productRequest);
      } else {
        productRequestRepository.save(productRequest);
      }
    }

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

}
