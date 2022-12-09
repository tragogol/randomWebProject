package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.*;
import com.zato.randomWebProject.repository.ProductRepository;
import com.zato.randomWebProject.repository.ProductStoreRepository;
import com.zato.randomWebProject.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class MarketPlaceController {

  private final BalanceService balanceService;
  private final ProductService productService;
  private final ProductRequestService productRequestService;
  private final ProductRepository productRepository;
  private final ProductStoreService productStoreService;
  private final ProductStoreRepository productStoreRepository;

  private final UserService userService;


  public MarketPlaceController(BalanceService balanceService, ProductService productService,
                               ProductRequestService productRequestService, ProductRepository productRepository,
                               ProductStoreService productStoreService, ProductStoreRepository productStoreRepository,
                               UserService userService) {
    this.balanceService = balanceService;
    this.productService = productService;
    this.productRequestService = productRequestService;
    this.productRepository = productRepository;
    this.productStoreService = productStoreService;
    this.productStoreRepository = productStoreRepository;
    this.userService = userService;
  }

  @GetMapping("marketplace/requests")
  public ResponseEntity<?> getRequests() {
    List<ProductRequest> requests = productRequestService.getRequestList();
    StringBuilder responseBody = new StringBuilder();
    for (ProductRequest request :
        requests) {
      responseBody.append(request.toString()).append("\n");
    }

    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
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
      productRequestService.createProductRequest(tmpProduct,tmpUser,quantity,price);
      productStoreService.changeStorageQuantity(tmpProduct, quantity, tmpUser);
      return ResponseEntity.status(HttpStatus.OK).body("Request replaced");
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something gone wrong");
    }
  }

  @GetMapping("marketplace/storage_list")
  public ResponseEntity<?> getStorageList() {
    List<ProductStore> selfStorage = productStoreService.getYourStorage(userService.findSelfUser());

    if (selfStorage.isEmpty()) return ResponseEntity.status(HttpStatus.OK).body("Empty");

    StringBuilder responseBody = new StringBuilder();
    for (ProductStore singleStore :
        selfStorage) {
      responseBody.append(singleStore.toString()).append("\n");
    }
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  @GetMapping("marketplace/buy_product/name={productName}&quantity={quantity}&minPrice={price}")
  public ResponseEntity<?> buyProduct(@PathVariable String productName, @PathVariable Long quantity,
                                      @PathVariable Double price) {
    Product product = productRepository.findByName(productName);
    if (product == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such a product");

    if (!productRequestService.isAvailableRequest(product, quantity, price))
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The request does not meet your requirements");

    Users user = userService.findSelfUser();
    Balance balance = balanceService.getBalance(user);
    if (balance == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad balance");

    ProductStore userStorage = productStoreRepository.findByUserIdAndProductId(user.getId(), product.getId());

    if (productRequestService.buyProduct(balance, userStorage, product, quantity, price)) {
      return ResponseEntity.status(HttpStatus.OK).body("Complete \n" + "Rest balance: " + balance.getBalanceValue());
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something gone wrong");
    }

  }

}
