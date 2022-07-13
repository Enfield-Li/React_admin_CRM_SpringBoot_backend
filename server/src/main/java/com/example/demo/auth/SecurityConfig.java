package com.example.demo.auth;

import static com.example.demo.auth.users.ApplicationUserPermission.*;
import static com.example.demo.auth.users.ApplicationUserRole.*;

import com.example.demo.auth.filters.AuthenticationFilter;
import com.example.demo.auth.filters.LoginFilter;
import com.example.demo.auth.filters.LogoutFilter;
import com.example.demo.auth.users.ApplicationUserService;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  // https://stackoverflow.com/a/71858153/16648127
  private static final String SWAGGER_UI_PATH_1 = "/swagger-ui/**";
  private static final String SWAGGER_UI_PATH_2 = "/v3/api-docs/**";

  private static final String LOGIN_ENDPOINT = "/sales/login";
  private static final String LOGOUT_ENDPOINT = "/sales/logout";

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
      .antMatchers("/", SWAGGER_UI_PATH_1, SWAGGER_UI_PATH_2)
      .permitAll()
      /*
       * 认证部分（Authorization）
       */
      // Authorities based permission
      .antMatchers(HttpMethod.POST, "/tags")
      .hasAuthority(CREATE_TAG.getPermission())
      .antMatchers(HttpMethod.PUT, "/companies")
      .hasAuthority(EDIT_COMPANY.getPermission())
      // Role based permission
      .antMatchers("/sales/update_role")
      .hasRole(SUPER_USER.name())
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
