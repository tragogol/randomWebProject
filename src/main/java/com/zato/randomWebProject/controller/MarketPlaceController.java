package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.ProductRequest;
import com.zato.randomWebProject.repository.ProductRepository;
import com.zato.randomWebProject.service.BalanceService;
import com.zato.randomWebProject.service.ProductRequestService;
import com.zato.randomWebProject.service.ProductService;
import com.zato.randomWebProject.service.ProductStoreService;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
public class MarketPlaceController {

  private final BalanceService balanceService;
  private final ProductService productService;
  private final ProductRequestService productRequestService;
  private final ProductRepository productRepository;
  private final ProductStoreService productStoreService;

  public MarketPlaceController(BalanceService balanceService, ProductService productService,
                               ProductRequestService productRequestService,
                               ProductRepository productRepository, ProductStoreService productStoreService) {
    this.balanceService = balanceService;
    this.productService = productService;
    this.productRequestService = productRequestService;
    this.productRepository = productRepository;
    this.productStoreService = productStoreService;
  }

  @GetMapping("marketplace/requests")
  public ResponseEntity<?> getRequests() {
    List<ProductRequest> requests = productRequestService.getRequestList();
    return ResponseEntity.status(HttpStatus.OK).body(requests.toString());
  }

  @PostMapping("marketplace/make_request")
  public ResponseEntity<?> postRequest(@RequestBody ProductRequest request) {
    if(productStoreService.isAvailable(request)) {
      productRequestService.ReplaceRequest(request);
      return ResponseEntity.status(HttpStatus.OK).body("Request replaced");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad request data");
    }
  }

  @PostMapping("marketplace/create_product/{productName}")
  public ResponseEntity<?> createNewProduct(@RequestBody ProductRequest productRequest) {
    return ResponseEntity.status(HttpStatus.OK).body("productName" + " " + productRequest.toString());
  }

  @GetMapping("marketplace/product_info/{id}")
  public ResponseEntity<?> productInfo(@PathVariable long id) {
    StringBuilder responseBody = new StringBuilder(productRepository.findById(id).getName() + "\n");
    responseBody.append(productRequestService.getProductPrice(id));
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  @PostMapping("marketplace/register_product")
  public ResponseEntity<?> productRegistration(@RequestBody Product product) {
    if (productService.createNewProduct(product)) {
      return ResponseEntity.status(HttpStatus.OK).body("Product registered ");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad product");
    }
  }
}
