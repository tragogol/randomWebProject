package com.zato.randomWebProject.data;

import javax.persistence.*;

@Entity
@Table(name = "tmpuser")
public class TmpUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "loginValue", unique = true, nullable = false)
    private String loginValue;
    private String passwordValue;

    public TmpUser() {}

    public TmpUser(String loginValue, String passwordValue) {
        this.loginValue = loginValue;
        this.passwordValue = passwordValue;
    }

    @Override
    public String toString() {
        return "TmpUser{" +
                "id=" + id +
                ", login='" + loginValue + '\'' +
                ", password='" + passwordValue + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }


    public void setId(Long id) {
        this.id = id;
    }

    public String getLoginValue() {
        return loginValue;
    }

    public void setLoginValue(String login) {
        this.loginValue = login;
    }

    public String getPassword() {
        return passwordValue;
    }

    public void setPassword(String password) {
        this.passwordValue = password;
    }
}
