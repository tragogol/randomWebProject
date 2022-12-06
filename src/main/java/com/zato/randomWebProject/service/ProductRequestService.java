package com.zato.randomWebProject.service;

import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.ProductRequest;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.ProductRequestRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

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
    return minPrice;
  }

  public double getProductPrice(long id) {

    Double minPrice = em.createQuery("SELECT Min(pr.price) FROM ProductRequest pr WHERE pr.product.id = " + id, Double.class).getSingleResult();
    return minPrice;
  }

  public List<ProductRequest> getRequestList() {
    return em.createQuery("SELECT pr FROM ProductRequest pr", ProductRequest.class).getResultList();
  }


}
