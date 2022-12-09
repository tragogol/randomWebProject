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


  public boolean buyProduct(Balance balance, ProductStore userStorage, Product product, Long quantity, Double price) {
    List<ProductRequest> tmpProductList = getRequestListByProductAndPrice(product, price);

    for (ProductRequest singleRequest :
        tmpProductList) {
      if (singleRequest.getQuantity() > quantity) {
        singleRequest.setQuantity(singleRequest.getQuantity() - quantity);
        productRequestRepository.save(singleRequest);

        userStorage.setQuantity(userStorage.getQuantity() + quantity);
        productStoreRepository.save(userStorage);

        balanceService.ChangeBalance(-quantity * singleRequest.getPrice(), balance.getUser());
        balanceService.ChangeBalance(quantity * singleRequest.getPrice(), singleRequest.getSeller());

        return true;
      } else {
        userStorage.setQuantity(userStorage.getQuantity() + singleRequest.getQuantity());
        productStoreRepository.save(userStorage);

        quantity -= singleRequest.getQuantity();
        balanceService.ChangeBalance(-quantity * singleRequest.getPrice(), balance.getUser());
        balanceService.ChangeBalance(quantity * singleRequest.getPrice(), singleRequest.getSeller());

        productRequestRepository.delete(singleRequest);
      }
      if (quantity == 0) {
        return true;
      }
    }

    return true;
  }

  private void checkRequest(ProductRequest productRequest) {
    if (productRequest.getQuantity() == 0) {
      productRequestRepository.delete(productRequest);
    } else {
      productRequestRepository.save(productRequest);
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
