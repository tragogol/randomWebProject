package com.zato.randomWebProject.config;

import com.zato.randomWebProject.data.Role;
import com.zato.randomWebProject.repository.RolesRepository;
import com.zato.randomWebProject.service.UserService;
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
  public BCryptPasswordEncoder bCryptPasswordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    httpSecurity
            .csrf()
            .disable()
            .authorizeRequests()
            //Доступ только для не зарегистрированных пользователей
            .antMatchers("/registration", "/login").not().fullyAuthenticated()
            .antMatchers("/logout").permitAll()
            //Доступ только для пользователей с ролью Администратор
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/contentPage").hasRole("user")
            .antMatchers("/getRole/**","/getRole").permitAll()

            //Доступ разрешен всем пользователей
            .antMatchers("/", "/resources/**").permitAll()
            //Все остальные страницы требуют аутентификации
            .anyRequest().authenticated()
            .and()
            //Настройка для входа в систему
            .logout()
            .permitAll()
            .logoutSuccessUrl("/");
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
