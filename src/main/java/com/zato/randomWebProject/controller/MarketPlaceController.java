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

  @GetMapping("marketplace/product_info")
  public ResponseEntity<?> productInfo(@RequestParam long id) {
    StringBuilder responseBody = new StringBuilder(productRepository.findById(id).getName() + "\n");
    responseBody.append(productRequestService.getProductPrice(id));
    return ResponseEntity.status(HttpStatus.OK).body(responseBody);
  }

  @GetMapping("marketplace/register_product")
  public ResponseEntity<?> productRegistration(@RequestParam String productName, @RequestParam long quantity) {
    Product newProduct = new Product();
    newProduct.setName(productName);

    newProduct = productService.createProductWithReturn(newProduct);
    if (newProduct != null) {
      Users tmpUser = userService.findSelfUser();
      productStoreService.AddProduct(newProduct, quantity, tmpUser);
      return ResponseEntity.status(HttpStatus.OK).body("Product registered " + newProduct);
    } else {
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something gone wrong");
    }
  }

  @GetMapping("marketplace/place_request")
  public ResponseEntity<?> placeRequest(@RequestParam String productName, @RequestParam long quantity, @RequestParam double price) {
    Users tmpUser = userService.findSelfUser();
    Product tmpProduct = productRepository.findByName(productName);
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

  @GetMapping("marketplace/buy_product")
  public ResponseEntity<?> buyProduct(@RequestParam String productName, @RequestParam Long quantity,
                                      @RequestParam Double price) {
    Product product = productRepository.findByName(productName);
    if (product == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No such a product");

    if (!productRequestService.isAvailableRequest(product, quantity, price))
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The request does not meet your requirements");

    Users user = userService.findSelfUser();
    Balance balance = balanceService.getBalance(user);
    if (balance == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Bad balance");

    ProductStore userStorage = productStoreRepository.findByUserIdAndProductId(user.getId(), product.getId());
    if (userStorage == null) userStorage = productStoreService.AddProduct(product, 0, user);
    if (productRequestService.buyProduct(balance, user, userStorage, product, quantity, price)) {
      return ResponseEntity.status(HttpStatus.OK).body("Complete \n" + "Rest balance: " + balance.getBalanceValue());
    } else {
      productStoreRepository.delete(userStorage);
      return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Something gone wrong");
    }

  }

}
