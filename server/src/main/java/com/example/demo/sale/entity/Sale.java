package com.example.demo.sale.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String first_name;
  private String last_name;
  private String email;

  public Sale(String first_name, String last_name, String email) {
    this.first_name = first_name;
    this.last_name = last_name;
    this.email = email;
  }

  public Sale of(String first_name, String last_name, String email) {
    return new Sale(first_name, last_name, email);
  }
}
