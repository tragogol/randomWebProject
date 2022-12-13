package com.zato.randomWebProject.service;

import com.zato.randomWebProject.controller.MarketPlaceController;
import com.zato.randomWebProject.controller.RegistrationController;
import com.zato.randomWebProject.data.*;
import com.zato.randomWebProject.repository.BalanceRepository;
import com.zato.randomWebProject.repository.ProductRepository;
import com.zato.randomWebProject.repository.ProductStoreRepository;
import com.zato.randomWebProject.repository.UsersRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServiceTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RegistrationController registrationController;

  @Autowired
  private MarketPlaceController marketPlaceController;
  @Autowired
  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;
  @Autowired
  private ProductStoreRepository productStoreRepository;

  private Users dummyUser;
  private final String username = "JOJO1234";
  private final String password = "12345678";
  private final String productName = "SUS";
  private final long quantityProduct = 1000L;
  private final String productTestName = "TESTNAME";

  @BeforeEach
  void setUp() {
    dummyUser = new Users();
    dummyUser.setUsername(username);
    dummyUser.setPassword(password);

    registrationController.addUser(dummyUser);
  }

  @Test
  @WithMockUser(username = username, password = password)
  public void registerProductCheckCorrectProduct() {
    marketPlaceController.productRegistration(productName, quantityProduct);
    try {
      productRepository.findByName(productName);
    } catch (NullPointerException e) {
      assert false;
    }
    assert Objects.equals(productRepository.findByName(productName).getName(), productName);
  }

  @Test
  @WithMockUser(username = username, password = password)
  public void registerProduct() {
    Product product = new Product();
    product.setName(productTestName);
    assert productService.createNewProduct(product);
  }

  @Test
  @WithMockUser(username = username, password = password)
  public void registerProductWithRepeat() {
    Product product = new Product();
    product.setName(productTestName);
    assert productService.createNewProduct(product);
    assert !productService.createNewProduct(product);
  }

  @Test
  @WithMockUser(username = username, password = password)
  public void registerProductWithReturn() {
    Product product = new Product();
    product.setName(productTestName);
    Product tmpProduct = productService.createProductWithReturn(product);
    assert Objects.equals(tmpProduct.getName(), product.getName());
  }

  @Test
  @WithMockUser(username = username, password = password)
  public void registerProductWithReturnWithRepeat() {
    Product product = new Product();
    product.setName(productTestName);
    Product firstProduct = productService.createProductWithReturn(product);
    Product secondProduct = productService.createProductWithReturn(product);
    assert secondProduct == null;
  }

  @Test
  @WithMockUser(username = username, password = password)
  public void getAllProduct() {
    int productCount = 10;
    List<Product> productListTemplates = new ArrayList<>();
    for (int i = 0; i < productCount; i++) {
      Product tmpProduct = new Product();
      tmpProduct.setName(productTestName + i);
      productListTemplates.add(tmpProduct);
      productService.createNewProduct(tmpProduct);
    }
    List<Product> productList = productService.getAllProduct();

    for (int i = 0; i < productListTemplates.size(); i++) {
      assert Objects.equals(productListTemplates.get(i).getName(), productList.get(i).getName());
    }
  }

  @Test
  @WithMockUser(username = username, password = password)
  public void registerProductCheckCorrectStorage() {
    marketPlaceController.productRegistration(productName, quantityProduct);
    try {
      productRepository.findByName(productName);
    } catch (NullPointerException e) {
      assert false;
    }
    Product product = productRepository.findByName(productName);
    assert productStoreRepository.findByUserIdAndProductId(dummyUser.getId(), product.getId()).getQuantity() == quantityProduct;
  }

  @AfterEach
  public void ResetRepository() {
    productRepository.deleteAll();
  }
}