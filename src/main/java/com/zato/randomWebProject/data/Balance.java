package com.zato.randomWebProject.data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
public class Balance {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Double balanceValue;

    public Balance() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getBalanceValue() {
        return balanceValue;
    }

    public void setBalanceValue(Double balance) {
        this.balanceValue = balance;
    }

    @Override
    public String toString() {
        return "UserBalance{" +
                "user_id=" + id +
                ", balance=" + balanceValue +
                '}';
    }
}
