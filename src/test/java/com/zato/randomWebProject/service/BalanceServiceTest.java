package com.zato.randomWebProject.service;

import com.zato.randomWebProject.controller.RegistrationController;
import com.zato.randomWebProject.data.Balance;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.BalanceRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Objects;

@SpringBootTest
@AutoConfigureMockMvc
class BalanceServiceTest {

    private Users dummyUser;
    private final String username = "JOJO1234";
    private final String password = "12345678";
    @Autowired
    private RegistrationController registrationController;
    @Autowired
    private BalanceService balanceService;
    @Autowired
    private UserService userService;
    @Autowired
    private BalanceRepository balanceRepository;


    @BeforeEach
    void setUp() {
        dummyUser = new Users();
        dummyUser.setUsername(username);
        dummyUser.setPassword(password);

        registrationController.addUser(dummyUser);
    }

    @Test
    @WithMockUser(username = username, password = password)
    void changeBalanceWithPositiveValue() {
        double valueToIncrease = 1000D;
        Users user = userService.findSelfUser();
        balanceService.createBalance(user);
        Balance balance = balanceService.getBalance(user);
        double startValue = balance.getBalanceValue();
        balanceService.changeBalance(valueToIncrease);
        balance = balanceService.getBalance(user);
        assert balance.getBalanceValue() == startValue + valueToIncrease;
    }

    @Test
    @WithMockUser(username = username, password = password)
    void changeBalanceWithNegativeValue() {
        double valueToIncrease = -500D;
        Users user = userService.findSelfUser();
        balanceService.createBalance(user);
        Balance balance = balanceService.getBalance(user);
        double startValue = balance.getBalanceValue();
        balanceService.changeBalance(valueToIncrease);
        balance = balanceService.getBalance(user);
        assert balance.getBalanceValue() == startValue + valueToIncrease;
    }

    @Test
    @WithMockUser(username = username, password = password)
    void changeBalanceWithDecreaseMoreThenStartValue() {
        double valueToIncrease;
        Users user = userService.findSelfUser();
        balanceService.createBalance(user);
        Balance balance = balanceService.getBalance(user);
        double startValue = balance.getBalanceValue();
        valueToIncrease = startValue * -2;
        assert !balanceService.changeBalance(valueToIncrease);
    }

    @Test
    @WithMockUser(username = username, password = password)
    void changeBalanceUserWithPositiveValue() {
        double valueToIncrease = 1000D;
        Users user = userService.findSelfUser();
        Balance balance = balanceService.getBalance(user);

        double startValue = balance.getBalanceValue();
        balanceService.changeBalance(valueToIncrease, user);
        balance = balanceService.getBalance(user);
        assert balance.getBalanceValue() == startValue + valueToIncrease;
    }

    @Test
    @WithMockUser(username = username, password = password)
    void changeBalanceUserWithNegativeValue() {
        double valueToIncrease = -500D;
        Users user = userService.findSelfUser();

        balanceService.createBalance(user);
        Balance balance = balanceService.getBalance(user);

        double startValue = balance.getBalanceValue();
        balanceService.changeBalance(valueToIncrease, user);
        balance = balanceService.getBalance(user);

        assert balance.getBalanceValue() == startValue + valueToIncrease;
    }

    @Test
    @WithMockUser(username = username, password = password)
    void changeBalanceUserWithDecreaseMoreThenStartValue() {
        double valueToIncrease;
        Users user = userService.findSelfUser();
        balanceService.createBalance(user);
        Balance balance = balanceService.getBalance(user);
        double startValue = balance.getBalanceValue();
        valueToIncrease = startValue * -2;
        assert !balanceService.changeBalance(valueToIncrease, user);
    }

    @Test
    @WithMockUser(username = username, password = password)
    void getBalance() {
        Users user = userService.findSelfUser();
        balanceService.createBalance(user);
        try {
            Balance balance = balanceService.getBalance(user);
            assert balance.getUser().equals(user);
            assert balance.getId() != null;
            assert balance.getBalanceValue() != null;
        } catch (NullPointerException e) {
            assert false;
            System.out.println("Balance not found");
        }
    }

    @Test
    @WithMockUser(username = username, password = password)
    void createBalance() {
        Users user = userService.findSelfUser();
        balanceService.createBalance(user);
        try {
            Balance balance = balanceRepository.findByUser(user);
            assert balance.getUser().equals(user);
            assert balance.getId() != null;
            assert balance.getBalanceValue() != null;
        } catch (NullPointerException e) {
            assert false;
            System.out.println("Balance not created");
        }
    }

    @AfterEach
    public void ResetAll() {
        balanceRepository.deleteAll();
    }

}