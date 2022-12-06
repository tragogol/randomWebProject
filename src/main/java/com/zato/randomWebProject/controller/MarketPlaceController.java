package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.ProductRequest;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.ProductRepository;
import com.zato.randomWebProject.service.*;
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
  private final UserService userService;


  public MarketPlaceController(BalanceService balanceService, ProductService productService,
                               ProductRequestService productRequestService, ProductRepository productRepository,
                               ProductStoreService productStoreService, UserService userService) {
    this.balanceService = balanceService;
    this.productService = productService;
    this.productRequestService = productRequestService;
    this.productRepository = productRepository;
    this.productStoreService = productStoreService;
    this.userService = userService;
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

  @GetMapping("marketplace/product_list")
  public ResponseEntity<?> productInfo() {
    List<Product> productList = productService.getAllProduct();
    StringBuilder responseBody = new StringBuilder();
    for (Product product:
         productList) {
      responseBody.append(product.toString()).append("\n");
    }

    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  @GetMapping("marketplace/product_info/{id}")
  public ResponseEntity<?> productInfo(@PathVariable long id) {
    StringBuilder responseBody = new StringBuilder(productRepository.findById(id).getName() + "\n");
    responseBody.append(productRequestService.getProductPrice(id));
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  @PostMapping("marketplace/register_product/{quantity}")
  public ResponseEntity<?> productRegistration(@RequestBody Product product, @PathVariable long quantity) {
    product = productService.createProductWithReturn(product);
    if (product != null) {
      Users tmpUser = userService.findSelfUser();
      productStoreService.AddProduct(product, quantity, tmpUser);
      return ResponseEntity.status(HttpStatus.OK).body("Product registered ");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something gone wrong");
    }
  }

  @PostMapping("marketplace/place_request/{productId}&{quantity}&{price}")
  public ResponseEntity<?> placeRequest(@PathVariable long productId, @PathVariable long quantity, @PathVariable double price) {
    Users tmpUser = userService.findSelfUser();
    Product tmpProduct = productRepository.findById(productId);
    if (productStoreService.isAvailable(tmpUser, tmpProduct, quantity)) {
      ProductRequest tmpRequest = productRequestService.createProductRequest(tmpProduct,tmpUser,quantity,price);
      productRequestService.ReplaceRequest(tmpRequest);
      return ResponseEntity.status(HttpStatus.OK).body("Request replaced " + tmpRequest.toString());
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something gone wrong");
    }
  }
}
