package com.example.demo.sale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Transient
  private String username;

  @JsonIgnore
  @Column(nullable = false)
  private String password;

  private String first_name;
  private String last_name;
  private String email;

  public Sale(
    String first_name,
    String last_name,
    String email,
    String password
  ) {
    this.first_name = first_name;
    this.last_name = last_name;
    this.email = email;
    this.password = password;
  }

  public Sale of(
    String first_name,
    String last_name,
    String email,
    String password
  ) {
    return new Sale(first_name, last_name, email, password);
  }

  public static String encode(
    String rawPassword,
    PasswordEncoder passwordEncoder
  ) {
    return passwordEncoder.encode(rawPassword);
  }

  public static Boolean matchPassword(
    String rawPassword,
    String password,
    PasswordEncoder passwordEncoder
  ) {
    return passwordEncoder.matches(rawPassword, password);
  }

  public String getUsername() {
    return this.getFirst_name() + " " + this.getLast_name();
  }
}
