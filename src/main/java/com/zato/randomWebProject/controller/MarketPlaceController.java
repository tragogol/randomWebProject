package com.zato.randomWebProject.controller;

import com.zato.randomWebProject.data.*;
import com.zato.randomWebProject.repository.ProductRepository;
import com.zato.randomWebProject.repository.ProductRequestRepository;
import com.zato.randomWebProject.repository.ProductStoreRepository;
import com.zato.randomWebProject.service.*;
import com.zato.randomWebProject.util.ProductRequestNotFoundException;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
public class MarketPlaceController {

  private final BalanceService balanceService;
  private final ProductService productService;
  private final ProductRequestService productRequestService;
  private final ProductRepository productRepository;
  private final ProductRequestRepository productRequestRepository;
  private final ProductStoreService productStoreService;
  private final ProductStoreRepository productStoreRepository;

  private final UserService userService;


  public MarketPlaceController(BalanceService balanceService, ProductService productService,
                               ProductRequestService productRequestService, ProductRepository productRepository,
                               ProductRequestRepository productRequestRepository, ProductStoreService productStoreService,
                               ProductStoreRepository productStoreRepository, UserService userService) {
    this.balanceService = balanceService;
    this.productService = productService;
    this.productRequestService = productRequestService;
    this.productRepository = productRepository;
    this.productRequestRepository = productRequestRepository;
    this.productStoreService = productStoreService;
    this.productStoreRepository = productStoreRepository;
    this.userService = userService;
  }

  @GetMapping("marketplace/requests")
  public CollectionModel<EntityModel<ProductRequestWOCredentials>> getRequests() {
    List<EntityModel<ProductRequestWOCredentials>> requests = new ArrayList<>();
    for (ProductRequest productRequest : productRequestRepository.findAll()) {
      ProductRequestWOCredentials tmp = new ProductRequestWOCredentials();
      tmp.copyValues(productRequest);
      EntityModel<ProductRequestWOCredentials> of = EntityModel.of(tmp,
          linkTo(methodOn(MarketPlaceController.class).getRequest(tmp.getId())).withSelfRel(),
          linkTo(methodOn(MarketPlaceController.class).getRequests()).withSelfRel());
      requests.add(of);
    }
    return CollectionModel.of(requests, linkTo(methodOn(MarketPlaceController.class).getRequests()).withSelfRel());
  }

  @GetMapping("marketPlace/requests/{id}")
  public EntityModel<ProductRequestWOCredentials> getRequest(@PathVariable Long id) {
    ProductRequest request = productRequestRepository.findById(id)
        .orElseThrow(ProductRequestNotFoundException::new);
    ProductRequestWOCredentials tmpProductRequest = new ProductRequestWOCredentials();
    tmpProductRequest.copyValues(request);
    return EntityModel.of(tmpProductRequest, linkTo(methodOn(MarketPlaceController.class).getRequest(id)).withSelfRel());
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
    responseBody.append(productRequestService.getMinProductPrice(id));
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
