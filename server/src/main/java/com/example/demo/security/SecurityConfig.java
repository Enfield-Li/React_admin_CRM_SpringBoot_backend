package com.example.demo.security;

import static com.example.demo.security.ApplicationUserPermission.COURSE_WRITE;
import static com.example.demo.security.ApplicationUserRole.ADMIN;
import static com.example.demo.security.ApplicationUserRole.ADMINTRAINEE;
import static com.example.demo.security.ApplicationUserRole.STUDENT;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final PasswordEncoder passwordEncoder;

  public SecurityConfig(PasswordEncoder passwordEncoder) {
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    // http.httpBasic().disable().csrf().disable();
    http
      .csrf()
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
      .httpBasic();
  }

  @Bean
  @Override
  protected UserDetailsService userDetailsService() {
    UserDetails student = User
      .builder()
      .username("student")
      .password(passwordEncoder.encode("student"))
      // .roles(STUDENT.name()) // ROLE_Student
      .authorities(STUDENT.getGrantedAuthorities())
      .build();

    UserDetails admin = User
      .builder()
      .username("admin")
      .password(passwordEncoder.encode("admin"))
      // .roles(ADMIN.name()) // ROLE_Admin
      .authorities(ADMIN.getGrantedAuthorities())
      .build();

    UserDetails adminTrainee = User
      .builder()
      .username("adminTrainee")
      .password(passwordEncoder.encode("adminTrainee"))
      // .roles(ADMINTRAINEE.name()) // ROLE_Admin
      .authorities(ADMINTRAINEE.getGrantedAuthorities())
      .build();

    return new InMemoryUserDetailsManager(student, admin, adminTrainee);
  }
  //   @Override
  //   public void configure(AuthenticationManagerBuilder authBuilder)
  //     throws Exception {}

  //   @Override
  //   public void configure(WebSecurity web) throws Exception {}
}
