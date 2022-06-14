package com.example.demo.sale.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Embedded
  @JsonIgnore
  private Password password;

  private String first_name;
  private String last_name;
  private String email;

  public Sale(
    String first_name,
    String last_name,
    String email,
    Password password
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
    Password password
  ) {
    return new Sale(first_name, last_name, email, password);
  }
}
