package com.zato.randomWebProject.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;

@Entity
public class Users implements UserDetails {
  @Id
  @GeneratedValue
  @Column(name = "user_id")
  private Long userId;
  @Size(min = 8)
  private String username;
  @Size(min = 8)
  private String password;
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Role> roles;

  //@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
  @OneToOne(cascade = CascadeType.ALL, optional = false)
  @JoinColumn(name = "id_balance")
  private Balance balance;

  public Users() {}

  public Long getUserId() {
    return userId;
  }

  public void setUserId(Long user_id) {
    this.userId = user_id;
  }

  @Override
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Set<Role> getRoles() {
    return roles;
  }

  public void setRoles(Set<Role> roles) {
    this.roles = roles;
  }

  public Balance getBalance() {
    return balance;
  }

  public void setBalance(Balance balance) {
    this.balance = balance;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return getRoles();
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public String toString() {
    return "Users{" +
            "user_id=" + userId +
            ", username='" + username + '\'' +
            ", password='" + password + '\'' +
            ", roles=" + roles.toString() +
            '}';
  }
}
