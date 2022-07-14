package com.example.demo.auth;

import static com.example.demo.auth.user.ApplicationUserPermission.*;
import static com.example.demo.auth.user.ApplicationUserRole.*;
import static com.example.demo.util.Constants.*;

import com.example.demo.auth.filter.AuthenticationFilter;
import com.example.demo.auth.filter.LoginFilter;
import com.example.demo.auth.filter.LogoutFilter;
import com.example.demo.auth.user.ApplicationUserService;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final ApplicationUserService saleService;

  public SecurityConfig(
    PasswordEncoder passwordEncoder,
    ApplicationUserService saleService
  ) {
    this.passwordEncoder = passwordEncoder;
    this.saleService = saleService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf()
      .disable()
      .formLogin()
      .disable()
      .formLogin()
      .disable()
      .logout()
      .disable()
      .authorizeRequests()
      .antMatchers(
        "/",
        TEST_ENDPOINT,
        REGISTER_ENDPOINT,
        SWAGGER_UI_PATH_1,
        SWAGGER_UI_PATH_2
      )
      .permitAll()
      
      /*
       * 认证部分（Authorization）
       */
      // Authorities based permission
      .antMatchers(HttpMethod.POST, TAGS_ENDPOINT)
      .hasAuthority(CREATE_TAG.getPermission())
      .antMatchers(HttpMethod.PUT, COMPANIES_ENDPOINT)
      .hasAuthority(EDIT_COMPANY.getPermission())

      // Role based permission
      .antMatchers(UPDATE_SALE_ENDPOINT)
      .hasRole(SUPER_USER.name())
      .antMatchers(HttpMethod.DELETE, COMPANIES_ENDPOINT)
      .hasAnyRole(SALE_ADMIN.name(), SUPER_USER.name())
      .antMatchers(HttpMethod.DELETE, DEALS_ENDPOINT)
      .hasAnyRole(SALE_ADMIN.name(), SUPER_USER.name())
      
      /*
       * 授权部分（Authentication）
       */
      .and()
      .addFilterBefore( // Login
        new LoginFilter(LOGIN_ENDPOINT, authenticationManager()),
        UsernamePasswordAuthenticationFilter.class
      )
      .addFilterBefore( // Logout
        new LogoutFilter(LOGOUT_ENDPOINT),
        UsernamePasswordAuthenticationFilter.class
      )
      .addFilterBefore( // Verify user on every request
        new AuthenticationFilter(),
        UsernamePasswordAuthenticationFilter.class
      );

    // Disable anonymousUser
    http.authorizeRequests().anyRequest().authenticated();
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
