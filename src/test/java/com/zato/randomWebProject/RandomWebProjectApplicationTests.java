package com.zato.randomWebProject;

import com.zato.randomWebProject.controller.MarketPlaceController;
import com.zato.randomWebProject.controller.RegistrationController;
import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.ProductRequest;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
class RandomWebProjectApplicationTests {

    @Autowired
    private RegistrationController registrationController;

    @Autowired
    private MarketPlaceController marketPlaceController;

    @Autowired
    private ProductRepository productRepository;


    @Autowired
    private ProductRequestRepository productRequestRepository;

    private Users seller;
    private final String productName = "SUS";
    private final long quantityProduct = 1000L;
    private final long quantityForSell = 50L;
    private final double sellPrice = 5;


    public void registerSeller() {
        seller = new Users();
        seller.setUsername("JOJO12345");
        seller.setPassword("12345678");

        registrationController.addUser(seller);
        assert seller != null;
    }

    public void registerProduct() {
        marketPlaceController.productRegistration(productName, quantityProduct);
        try {
            productRepository.findByName(productName);
        } catch (NullPointerException e) {
            assert false;
        }
        assert Objects.equals(productRepository.findByName(productName).getName(), productName);
    }

    public void placeRequest() {
        marketPlaceController.placeRequest(productName, quantityForSell, sellPrice);
        Product sellProduct = productRepository.findByName(productName);
        ProductRequest productRequest = productRequestRepository.findBySellerAndProductAndQuantityAndPrice(seller, sellProduct, quantityForSell, sellPrice);
        assert productRequest.getQuantity() == quantityForSell;
        assert Objects.equals(seller.getUsername(), productRequest.getSeller().getUsername());
        assert productRequest.getPrice() == sellPrice;
    }



    @Test
    @WithMockUser(username = "JOJO12345", password = "12345678")
    public void registerUserProductRequest() throws Exception {
        registerSeller();
        registerProduct();
        placeRequest();
        assert true;
    }






}
