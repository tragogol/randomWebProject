package com.zato.randomWebProject.data;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Objects;
import java.util.Set;

@Entity
public class Users implements UserDetails {
  @Id
  @GeneratedValue
  private Long id;
  @Size(min = 8)
  private String username;
  @Size(min = 8)
  private String password;
  @ManyToMany(fetch = FetchType.EAGER)
  private Set<Role> roles;


  public Users() {}

  public Long getId() {
    return id;
  }

  public void setId(Long user_id) {
    this.id = user_id;
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
            "user_id=" + id +
            ", username='" + username +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Users users = (Users) o;
    return Objects.equals(id, users.id) && Objects.equals(username, users.username) && Objects.equals(password, users.password);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, username, password, roles);
  }
}
