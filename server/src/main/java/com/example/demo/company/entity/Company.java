package com.example.demo.company.entity;

import static javax.persistence.CascadeType.DETACH;

import com.example.demo.sale.entity.Sale;
import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Company {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Instant created_at;

  @Column(name = "linked_in")
  private String linkedIn;

  @Column(name = "state_abbr")
  private String stateAbbr;

  private String name;
  private String logo;
  private String sector;
  private String website;
  private String phone_number;
  private String address;
  private String zipcode;
  private String city;

  private Integer size;
  private Integer nb_contacts;
  private Integer nb_deals;

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @ManyToOne(cascade = DETACH)
  @JoinColumn(name = "sales_id")
  private Sale sale;
}
