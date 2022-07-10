package com.example.demo.entity;

import com.example.demo.auth.users.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Collection;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @JsonIgnore
  @Column(nullable = false)
  private String password;

  private String first_name;
  private String last_name;
  private String email;
  private String role;

  @Transient
  private String username;

  @Transient
  private List<GrantedAuthority> authorities;

  public Sale(
    String first_name,
    String last_name,
    String email,
    String password,
    ApplicationUserRole saleRole
  ) {
    this.first_name = first_name;
    this.last_name = last_name;
    this.email = email;
    this.password = password;
    this.role = saleRole.toString().toLowerCase();
  }

  public Sale of(
    String first_name,
    String last_name,
    String email,
    String password,
    ApplicationUserRole saleRole
  ) {
    return new Sale(first_name, last_name, email, password, saleRole);
  }

  public String getUsername() {
    return this.getFirst_name() + " " + this.getLast_name();
  }

  public String getRoles() {
    return role;
  }

  public void setRole(ApplicationUserRole saleRole) {
    this.role = saleRole.toString().toLowerCase();
  }
}
