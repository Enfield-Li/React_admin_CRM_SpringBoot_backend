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
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
public class Deal {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @Column(length = 1000, name = "_description")
  private String description;

  @Column(name = "_index")
  private Integer index;

  private Date created_at;
  private Date updated_at;
  private Date start_at;

  private String name;
  private Long amount;
  private Integer nb_notes;
  private String type;
  private String stage;

  @Transient
  private String contactIdsString;

  @Transient
  private List<Long> contact_ids = new ArrayList<>();

  @JsonIgnore
  @OneToMany(
    mappedBy = "deal",
    cascade = { DETACH, DETACH, MERGE },
    fetch = LAZY
  )
  private Set<DealNote> dealNote = new HashSet<>();

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @JsonIgnore
  @ToString.Exclude
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @Column(updatable = false, insertable = false)
  private Long company_id;

  @JsonIgnore
  @ToString.Exclude
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  @JsonIgnore
  @ManyToMany(cascade = { PERSIST, DETACH, MERGE }, fetch = LAZY)
  @JoinTable(
    name = "deal_contact",
    joinColumns = @JoinColumn(name = "deal_id"),
    inverseJoinColumns = @JoinColumn(name = "contact_id"),
    uniqueConstraints = @UniqueConstraint(
      columnNames = { "deal_id", "contact_id" }
    )
  )
  private Set<Contact> contact_list = new HashSet<>();

  public Deal() {}

  public Deal(
    String name,
    String description,
    String stage,
    String type,
    Long amount,
    Sale sale,
    Company company
  ) {
    this.name = name;
    this.description = description;
    this.stage = stage;
    this.type = type;
    this.amount = amount;
    this.sale = sale;
    this.company = company;
  }

  public void addContact(Contact contact) {
    contact_list.add(contact);
  }

  public Long getId() {
    return this.id;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Integer getIndex() {
    return this.index;
  }

  public void setIndex(Integer index) {
    this.index = index;
  }

  public Date getCreated_at() {
    return this.created_at;
  }

  public void setCreated_at(Date created_at) {
    this.created_at = created_at;
  }

  public Date getUpdated_at() {
    return this.updated_at;
  }

  public void setUpdated_at(Date updated_at) {
    this.updated_at = updated_at;
  }

  public Date getStart_at() {
    return this.start_at;
  }

  public void setStart_at(Date start_at) {
    this.start_at = start_at;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Long getAmount() {
    return this.amount;
  }

  public void setAmount(Long amount) {
    this.amount = amount;
  }

  public Integer getNb_notes() {
    return this.nb_notes;
  }

  public void setNb_notes(Integer nb_notes) {
    this.nb_notes = nb_notes;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getStage() {
    return this.stage;
  }

  public void setStage(String stage) {
    this.stage = stage;
  }

  public String getContactIdsString() {
    return this.contactIdsString;
  }

  public void setContactIdsString(String contactIdsString) {
    this.contactIdsString = contactIdsString;
  }

  public List<Long> getContact_ids() {
    return this.contact_ids;
  }

  public void setContact_ids(List<Long> contact_ids) {
    this.contact_ids = contact_ids;
  }

  public Set<DealNote> getDealNote() {
    return this.dealNote;
  }

  public void setDealNote(Set<DealNote> dealNote) {
    this.dealNote = dealNote;
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

  public Set<Contact> getContact_list() {
    return this.contact_list;
  }

  public void setContact_list(Set<Contact> contact_list) {
    this.contact_list = contact_list;
  }
}
