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
import lombok.ToString;

@Entity
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
  @ToString.Exclude
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

  public Company() {}

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

  public Long getId() {
    return this.id;
  }

  public Date getCreated_at() {
    return this.created_at;
  }

  public void setCreated_at(Date created_at) {
    this.created_at = created_at;
  }

  public String getLinkedIn() {
    return this.linkedIn;
  }

  public void setLinkedIn(String linkedIn) {
    this.linkedIn = linkedIn;
  }

  public String getStateAbbr() {
    return this.stateAbbr;
  }

  public void setStateAbbr(String stateAbbr) {
    this.stateAbbr = stateAbbr;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getLogo() {
    return this.logo;
  }

  public void setLogo(String logo) {
    this.logo = logo;
  }

  public String getSector() {
    return this.sector;
  }

  public void setSector(String sector) {
    this.sector = sector;
  }

  public String getWebsite() {
    return this.website;
  }

  public void setWebsite(String website) {
    this.website = website;
  }

  public String getPhone_number() {
    return this.phone_number;
  }

  public void setPhone_number(String phone_number) {
    this.phone_number = phone_number;
  }

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getZipcode() {
    return this.zipcode;
  }

  public void setZipcode(String zipcode) {
    this.zipcode = zipcode;
  }

  public String getCity() {
    return this.city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public Integer getSize() {
    return this.size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  public Integer getNb_contacts() {
    return this.nb_contacts;
  }

  public void setNb_contacts(Integer nb_contacts) {
    this.nb_contacts = nb_contacts;
  }

  public Integer getNb_deals() {
    return this.nb_deals;
  }

  public void setNb_deals(Integer nb_deals) {
    this.nb_deals = nb_deals;
  }

  public Long getSales_id() {
    return this.sales_id;
  }

  public void setSales_id(Long sales_id) {
    this.sales_id = sales_id;
  }

  public Sale getSale() {
    return this.sale;
  }

  public void setSale(Sale sale) {
    this.sale = sale;
  }

  public Set<Contact> getContact() {
    return this.contact;
  }

  public void setContact(Set<Contact> contact) {
    this.contact = contact;
  }
}
