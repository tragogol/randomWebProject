package com.zato.randomWebProject.service;

import com.zato.randomWebProject.controller.RegistrationController;
import com.zato.randomWebProject.data.Users;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import static org.junit.jupiter.api.Assertions.*;

class BalanceServiceTest {

    private Users dummyUser;
    @Autowired
    private RegistrationController registrationController;

    @BeforeEach
    void setUp() {
        dummyUser = new Users();
        dummyUser.setUsername("JOJO12345");
        dummyUser.setPassword("12345678");

        registrationController.addUser(dummyUser);
    }

    @Test
    @WithMockUser(username = "JOJO12345", password = "12345678")
    void changeBalance() {
    }

    @Test
    @WithMockUser(username = "JOJO12345", password = "12345678")
    void testChangeBalance() {
    }

    @Test
    @WithMockUser(username = "JOJO12345", password = "12345678")
    void getBalance() {
    }

    @Test
    void createBalance() {
    }
}