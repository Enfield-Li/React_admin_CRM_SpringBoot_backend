package com.example.demo.auth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final SaleService saleService;
  // https://stackoverflow.com/a/71858153/16648127
  private static final String SWAGGER_UI_PATH_1 = "/swagger-ui/**";
  private static final String SWAGGER_UI_PATH_2 = "/v3/api-docs/**";

  public SecurityConfig(
    PasswordEncoder passwordEncoder,
    SaleService saleService
  ) {
    this.passwordEncoder = passwordEncoder;
    this.saleService = saleService;
  }

  @Override
  public void configure(WebSecurity web) throws Exception {
    web.ignoring().antMatchers(SWAGGER_UI_PATH_1, SWAGGER_UI_PATH_2);
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf()
      .disable()
      .authorizeRequests()
      .antMatchers("/")
      .permitAll()// .anyRequest()
    //   .authenticated()
    // .hasAnyRole(SALE_PERSON.name(), SALE_ADMIN.name());
    // .antMatchers("/")
    // .authorizeRequests()
    // .antMatchers("/")
    // .permitAll()
    // .antMatchers("/sign-up")
    // .permitAll()
    // .antMatchers("/sign-in")
    // .permitAll()
    // .anyRequest()
    // .authenticated()
    // .and()
    // .formLogin()
    // .loginPage("/")
    // .loginProcessingUrl("/sales/login")
    //   .permitAll()
    ;
  }

  @Override
  protected void configure(AuthenticationManagerBuilder auth) throws Exception {
    auth.authenticationProvider(daoAuthenticationProvider());
  }

  public DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(saleService);
    return provider;
  }
}
