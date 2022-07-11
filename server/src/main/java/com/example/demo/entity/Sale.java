package com.example.demo.entity;

import static com.example.demo.auth.users.ApplicationUserRole.SALE_ADMIN;
import static com.example.demo.auth.users.ApplicationUserRole.SALE_PERSON;
import static com.example.demo.auth.users.ApplicationUserRole.SUPER_USER;

import com.example.demo.auth.users.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
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

  private String role;
  private String email;
  private String status;
  private String last_name;
  private String first_name;

  @Transient
  private String username;

  public String getUsername() {
    if (last_name == null) {
      return first_name;
    }

    return first_name + " " + last_name;
  }

  // Based on user role, return respective authorities
  public List<GrantedAuthority> getUserAuthorities() {
    List<GrantedAuthority> authorities = null;

    switch (role) {
      case "SALE_PERSON":
        authorities = SALE_PERSON.getGrantedAuthorities();
        break;
      case "SALE_ADMIN":
        authorities = SALE_ADMIN.getGrantedAuthorities();
        break;
      case "SUPER_USER":
        authorities = SUPER_USER.getGrantedAuthorities();
        break;
      default:
        break;
    }

    return authorities;
  }

  public void setRole(ApplicationUserRole saleRole) {
    this.role = saleRole.name();
  }
}
