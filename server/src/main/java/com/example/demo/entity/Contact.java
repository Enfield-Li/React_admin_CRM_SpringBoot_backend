package com.example.demo.entity;

import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
public class Contact {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String first_name;
  private String last_name;
  private String gender;
  private String title;
  private String email;
  private String phone_number1;
  private String phone_number2;

  private Integer nb_notes;
  private String status;
  private String background;
  private String acquisition;

  private Date first_seen;
  private Date last_seen;
  private Boolean has_newsletter;

  @Transient
  private List<Integer> tags = new ArrayList<>();

  @Transient
  private String raw_tags;

  @Column(updatable = false, insertable = false)
  private Long company_id;

  @JsonIgnore
  @ToString.Exclude
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @JsonIgnore
  @ToString.Exclude
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @JsonIgnore
  @OneToMany(
    mappedBy = "contact",
    fetch = LAZY,
    cascade = { PERSIST, DETACH, MERGE }
  )
  private Set<ContactNote> contactNote = new HashSet<>();

  // https://thorben-janssen.com/hibernate-tips-the-best-way-to-remove-entities-from-a-many-to-many-association/#1_Use_a_Set_instead_of_a_List
  @JsonIgnore
  @ManyToMany(cascade = { PERSIST, DETACH, MERGE }, fetch = LAZY)
  @JoinTable(
    name = "contact_tag",
    joinColumns = @JoinColumn(name = "contact_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"),
    uniqueConstraints = @UniqueConstraint(
      columnNames = { "contact_id", "tag_id" }
    )
  )
  private Set<Tags> tag_list = new HashSet<>();

  @JsonIgnore
  @ManyToMany(cascade = { PERSIST, DETACH, MERGE }, fetch = LAZY)
  private Set<Deal> deals = new HashSet<>();

  public void addTags(Tags tag) {
    tag_list.add(tag);
  }

  public void addDeals(Deal deal) {
    deals.add(deal);
  }

  public Contact() {}

  public Contact(
    String first_name,
    String last_name,
    String title,
    String status,
    Date last_seen,
    Company company,
    Sale sale,
    String background
  ) {
    this.first_name = first_name;
    this.last_name = last_name;
    this.title = title;
    this.status = status;
    this.last_seen = last_seen;
    this.company = company;
    this.sale = sale;
    this.background = background;
  }

  public Long getId() {
    return this.id;
  }

  public String getFirst_name() {
    return this.first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getLast_name() {
    return this.last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getGender() {
    return this.gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public String getTitle() {
    return this.title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhone_number1() {
    return this.phone_number1;
  }

  public void setPhone_number1(String phone_number1) {
    this.phone_number1 = phone_number1;
  }

  public String getPhone_number2() {
    return this.phone_number2;
  }

  public void setPhone_number2(String phone_number2) {
    this.phone_number2 = phone_number2;
  }

  public Integer getNb_notes() {
    return this.nb_notes;
  }

  public void setNb_notes(Integer nb_notes) {
    this.nb_notes = nb_notes;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getBackground() {
    return this.background;
  }

  public void setBackground(String background) {
    this.background = background;
  }

  public String getAcquisition() {
    return this.acquisition;
  }

  public void setAcquisition(String acquisition) {
    this.acquisition = acquisition;
  }

  public Date getFirst_seen() {
    return this.first_seen;
  }

  public void setFirst_seen(Date first_seen) {
    this.first_seen = first_seen;
  }

  public Date getLast_seen() {
    return this.last_seen;
  }

  public void setLast_seen(Date last_seen) {
    this.last_seen = last_seen;
  }

  public Boolean isHas_newsletter() {
    return this.has_newsletter;
  }

  public Boolean getHas_newsletter() {
    return this.has_newsletter;
  }

  public void setHas_newsletter(Boolean has_newsletter) {
    this.has_newsletter = has_newsletter;
  }

  public List<Integer> getTags() {
    return this.tags;
  }

  public void setTags(List<Integer> tags) {
    this.tags = tags;
  }

  public String getRaw_tags() {
    return this.raw_tags;
  }

  public void setRaw_tags(String raw_tags) {
    this.raw_tags = raw_tags;
  }

  public Long getCompany_id() {
    return this.company_id;
  }

  public void setCompany_id(Long company_id) {
    this.company_id = company_id;
  }

  public Company getCompany() {
    return this.company;
  }

  public void setCompany(Company company) {
    this.company = company;
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

  public Set<ContactNote> getContactNote() {
    return this.contactNote;
  }

  public void setContactNote(Set<ContactNote> contactNote) {
    this.contactNote = contactNote;
  }

  public Set<Tags> getTag_list() {
    return this.tag_list;
  }

  public void setTag_list(Set<Tags> tag_list) {
    this.tag_list = tag_list;
  }
}
