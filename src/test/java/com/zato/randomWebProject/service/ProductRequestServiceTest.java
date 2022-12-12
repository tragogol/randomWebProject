package com.zato.randomWebProject.service;

import com.zato.randomWebProject.controller.MarketPlaceController;
import com.zato.randomWebProject.controller.RegistrationController;
import com.zato.randomWebProject.data.*;
import com.zato.randomWebProject.repository.BalanceRepository;
import com.zato.randomWebProject.repository.ProductRepository;
import com.zato.randomWebProject.repository.ProductStoreRepository;
import com.zato.randomWebProject.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Objects;

@SpringBootTest
@AutoConfigureMockMvc
class ProductRequestServiceTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private RegistrationController registrationController;

  @Autowired
  private MarketPlaceController marketPlaceController;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private UsersRepository usersRepository;

  @Autowired
  private BalanceRepository balanceRepository;

  @Autowired
  private ProductStoreRepository productStoreRepository;

  @Autowired
  private UserService userService;

  private Users dummyUser;
  private final String productName = "SUS";
  private final long quantityProduct = 1000L;

  @BeforeEach
  void setUp() {
    dummyUser = new Users();
    dummyUser.setUsername("JOJO12345");
    dummyUser.setPassword("12345678");

    registrationController.addUser(dummyUser);
  }


  @Test
  @WithMockUser(username = "JOJO12345", password = "12345678")
  public void registerProductCheckCorrectProduct() {
    marketPlaceController.productRegistration(productName, quantityProduct);
    try {
      productRepository.findByName(productName);
    } catch (NullPointerException e) {
      assert false;
    }
    assert Objects.equals(productRepository.findByName(productName).getName(), productName);
    Product product = productRepository.findByName(productName);
    assert productStoreRepository.findByUserIdAndProductId(dummyUser.getId(), product.getId()).getQuantity() == quantityProduct;
  }





}