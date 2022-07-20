package com.example.demo.entity;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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

  private Date created_at;

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

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @JsonIgnore
  @OneToMany(
    mappedBy = "company",
    cascade = { PERSIST, DETACH, MERGE },
    fetch = LAZY
  )
  private Set<Contact> contact = new HashSet<>();

  public Company(
    Sale sale,
    String name,
    String city,
    Integer size,
    String sector,
    String stateAbbr
  ) {
    this.sale = sale;
    this.name = name;
    this.city = city;
    this.size = size;
    this.sector = sector;
    this.stateAbbr = stateAbbr;
  }
}
