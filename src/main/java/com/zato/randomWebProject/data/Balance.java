package com.zato.randomWebProject.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Balance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long balance_id;
    @NotNull
    private Double balanceValue;

    @OneToOne
    @MapsId
    private Users user;

    public Balance() {}

    public Long getBalance_id() {
        return balance_id;
    }

    public void setBalance_id(Long id) {
        this.balance_id = id;
    }

    public Double getBalanceValue() {
        return balanceValue;
    }

    public void setBalanceValue(Double balance) {
        this.balanceValue = balance;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "UserBalance{" +
                "user_id=" + balance_id +
                ", balance=" + balanceValue +
                '}';
    }
}
