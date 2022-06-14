package com.example.demo.sale.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import lombok.Data;
import org.springframework.security.crypto.password.PasswordEncoder;

@Data
@Embeddable
public class Password {

  @Column(nullable = false)
  private String password;

  protected Password() {}

  private Password(String password) {
    this.password = password;
  }

  public static Password encode(
    String rawPassword,
    PasswordEncoder passwordEncoder
  ) {
    return new Password(passwordEncoder.encode(rawPassword));
  }

  public Boolean matchPassword(
    String rawPassword,
    PasswordEncoder passwordEncoder
  ) {
    return passwordEncoder.matches(rawPassword, password);
  }
}
