package com.example.demo.security;

import static com.example.demo.security.ApplicationUserPermission.COURSE_WRITE;
import static com.example.demo.security.ApplicationUserRole.ADMIN;
import static com.example.demo.security.ApplicationUserRole.ADMINTRAINEE;
import static com.example.demo.security.ApplicationUserRole.STUDENT;

import com.example.demo.auth.ApplicationUserService;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;
  private final ApplicationUserService applicationUserService;

  public SecurityConfig(
    PasswordEncoder passwordEncoder,
    ApplicationUserService applicationUserService
  ) {
    this.passwordEncoder = passwordEncoder;
    this.applicationUserService = applicationUserService;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // http.httpBasic().disable().csrf().disable();
    http
      .csrf()
      // .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
      // .and()
      .disable()
      .authorizeRequests()
      .antMatchers("/", "index", "/css/*", "/js/*")
      .permitAll()
      .antMatchers("/api/**")
      .hasRole(STUDENT.name())
      // .antMatchers(HttpMethod.DELETE, "/management/api/**")
      // .hasAuthority(COURSE_WRITE.getPermission())
      // .antMatchers(HttpMethod.POST, "/management/api/**")
      // .hasAuthority(COURSE_WRITE.getPermission())
      // .antMatchers(HttpMethod.PUT, "/management/api/**")
      // .hasAuthority(COURSE_WRITE.getPermission())
      // .antMatchers("/management/api/**")
      // .hasAnyRole(ADMIN.name(), ADMINTRAINEE.name())
      .anyRequest()
      .authenticated()
      .and()
      .formLogin()
      .loginPage("/login")
      .permitAll()
      .defaultSuccessUrl("/courses", true)
      .passwordParameter("password")
      .usernameParameter("username")
      .and()
      .rememberMe()
      .tokenValiditySeconds((int) TimeUnit.DAYS.toSeconds(21))
      .key("somethingverysecured")
      .rememberMeParameter("remember-me")
      .and()
      .logout()
      .logoutUrl("/logout")
      .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET")) // https://docs.spring.io/spring-security/site/docs/4.2.12.RELEASE/apidocs/org/springframework/security/config/annotation/web/configurers/LogoutConfigurer.html
      .clearAuthentication(true)
      .invalidateHttpSession(true)
      .deleteCookies("JSESSIONID", "remember-me")
      .logoutSuccessUrl("/login");
    // .httpBasic();
  }

  @Bean
  DaoAuthenticationProvider daoAuthenticationProvider() {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
    provider.setPasswordEncoder(passwordEncoder);
    provider.setUserDetailsService(applicationUserService);
    return provider;
  }

  @Override
  public void configure(AuthenticationManagerBuilder authBuilder)
    throws Exception {
    authBuilder.authenticationProvider(daoAuthenticationProvider());
  }
  // @Bean
  // @Override
  // protected UserDetailsService userDetailsService() {
  //   UserDetails student = User
  //     .builder()
  //     .username("student")
  //     .password(passwordEncoder.encode("student"))
  //     // .roles(STUDENT.name()) // ROLE_Student
  //     .authorities(STUDENT.getGrantedAuthorities())
  //     .build();

  //   UserDetails admin = User
  //     .builder()
  //     .username("admin")
  //     .password(passwordEncoder.encode("admin"))
  //     // .roles(ADMIN.name()) // ROLE_Admin
  //     .authorities(ADMIN.getGrantedAuthorities())
  //     .build();

  //   UserDetails adminTrainee = User
  //     .builder()
  //     .username("adminTrainee")
  //     .password(passwordEncoder.encode("adminTrainee"))
  //     // .roles(ADMINTRAINEE.name()) // ROLE_Admin
  //     .authorities(ADMINTRAINEE.getGrantedAuthorities())
  //     .build();

  //   return new InMemoryUserDetailsManager(student, admin, adminTrainee);
  // }

  //   @Override
  //   public void configure(WebSecurity web) throws Exception {}
}
