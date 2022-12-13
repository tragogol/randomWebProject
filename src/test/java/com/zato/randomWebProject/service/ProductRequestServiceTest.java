package com.zato.randomWebProject.service;

import com.zato.randomWebProject.controller.RegistrationController;
import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.ProductRepository;
import com.zato.randomWebProject.repository.ProductRequestRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
class ProductRequestServiceTest {

  private Product testProduct;
  private Users dummyUser;
  private final String username = "JOJO1234";
  private final String password = "12345678";

  @Autowired
  private RegistrationController registrationController;
  @Autowired
  private ProductRequestService productRequestService;
  @Autowired
  private ProductRequestRepository productRequestRepository;
  @Autowired
  private ProductService productService;
  @Autowired
  private ProductRepository productRepository;
  @BeforeEach
  void setUp() {
    dummyUser = new Users();
    dummyUser.setUsername(username);
    dummyUser.setPassword(password);

    registrationController.addUser(dummyUser);

    testProduct = new Product();
    testProduct.setName("PRODUCTNAME");
    productService.createNewProduct(testProduct);
  }

  @AfterEach
  void tearDown() {
    productRequestRepository.deleteAll();
    productRepository.deleteAll();
  }

  @Test
  @WithMockUser(username = username, password = password)
  void createProductRequest() {

  }

  @Test
  @WithMockUser(username = username, password = password)
  void getProductPrice() {

  }

  @Test
  @WithMockUser(username = username, password = password)
  void testGetProductPrice() {
  }

  @Test
  @WithMockUser(username = username, password = password)
  void getRequestList() {
  }

  @Test
  @WithMockUser(username = username, password = password)
  void getRequestListByProduct() {
  }

  @Test
  @WithMockUser(username = username, password = password)
  void getRequestListByProductAndPrice() {
  }

  @Test
  @WithMockUser(username = username, password = password)
  void buyProduct() {
  }

  @Test
  @WithMockUser(username = username, password = password)
  void isAvailableRequest() {
  }
}