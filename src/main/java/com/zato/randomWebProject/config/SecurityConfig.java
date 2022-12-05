package com.zato.randomWebProject.config;

import com.zato.randomWebProject.data.Balance;
import com.zato.randomWebProject.data.Product;
import com.zato.randomWebProject.data.Role;
import com.zato.randomWebProject.data.Users;
import com.zato.randomWebProject.repository.BalanceRepository;
import com.zato.randomWebProject.repository.ProductRepository;
import com.zato.randomWebProject.repository.RolesRepository;
import com.zato.randomWebProject.repository.UsersRepository;
import com.zato.randomWebProject.service.UserService;
import org.h2.engine.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.swing.plaf.SeparatorUI;

@Configuration
class SecurityConfig extends WebSecurityConfigurerAdapter {

  @Autowired
  public UserService userService;

  @Bean
  CommandLineRunner initRoles(RolesRepository repository) {
    return args -> {
      repository.save(new Role(1L, "USER"));
      repository.save(new Role(2L, "ADMIN"));
    };
  }

  @Bean
  CommandLineRunner initUsers(UsersRepository repository, RolesRepository rolesRepository) {
    return args -> {
      Users tmpUser = new Users();
      tmpUser.setUsername("JOJO1234");
      tmpUser.setPassword("12345678");
      userService.saveUser(tmpUser);

      tmpUser = new Users();
      tmpUser.setUsername("KIKI1234");
      tmpUser.setPassword("43211234");
      userService.saveUser(tmpUser);

    };
  }

  @Bean
  CommandLineRunner initProducts(ProductRepository productRepository) {
    return args -> {
      Product tmpProduct = new Product();
      tmpProduct.setName("ABOBUS");

    };
  }


  @Bean
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf()
            .disable()
            .authorizeRequests()

            .antMatchers("/", "/resources/**", "/logout", "/registration", "/login", "/marketplace/**", "/marketplace").permitAll()
            .antMatchers("/contentPage", "/getRole/**","/getRole").fullyAuthenticated()
            .anyRequest().authenticated()
            .and()
            .logout()
            .permitAll()
            .logoutSuccessUrl("/login");
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web
            .ignoring()
            .antMatchers("/h2-console/**");
  }

  @Autowired
  protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
    auth.userDetailsService(userService).passwordEncoder(bCryptPasswordEncoder());
  }

  @Bean
  @Override
  public AuthenticationManager authenticationManagerBean() throws Exception {
    return super.authenticationManagerBean();
  }



}
