package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;

  public SecurityConfig(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // http.httpBasic().disable().csrf().disable();
    http
      .authorizeRequests()
      .antMatchers("/", "index")
      .permitAll()
      .anyRequest()
      .authenticated()
      .and()
      .httpBasic();
  }

  @Bean
  @Override
  protected UserDetailsService userDetailsService() {
    UserDetails userOne = User
      .builder()
      .username("student1")
      .password(passwordEncoder.encode("student1"))
      .roles("Student") // ROLE_Student
      .build();

    return new InMemoryUserDetailsManager(userOne);
  }
  //   @Override
  //   public void configure(AuthenticationManagerBuilder authBuilder)
  //     throws Exception {}

  //   @Override
  //   public void configure(WebSecurity web) throws Exception {}
}
