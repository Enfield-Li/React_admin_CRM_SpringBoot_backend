package com.example.demo.entity;

import static com.example.demo.auth.user.ApplicationUserRole.*;
import static javax.persistence.CascadeType.*;
import static javax.persistence.FetchType.LAZY;

import com.example.demo.auth.user.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(
  uniqueConstraints = {
    @UniqueConstraint(columnNames = { "first_name", "last_name" }),
  }
)
public class Sale {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String email;
  private String status;
  private String last_name;

  @NotNull
  private String first_name;

  @NotNull
  @JsonIgnore
  private String password;

  // https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/
  @NotNull
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private ApplicationUserRole role;

  @Transient
  private String username;

  @JsonIgnore
  @OneToMany(
    mappedBy = "sale",
    cascade = { PERSIST, DETACH, MERGE },
    fetch = LAZY
  )
  private Set<Company> companies = new HashSet<>();

  @JsonIgnore
  @OneToMany(
    mappedBy = "sale",
    cascade = { PERSIST, DETACH, MERGE },
    fetch = LAZY
  )
  private Set<Task> tasks = new HashSet<>();

  @JsonIgnore
  @OneToMany(
    mappedBy = "sale",
    cascade = { PERSIST, DETACH, MERGE },
    fetch = LAZY
  )
  private Set<Deal> deals = new HashSet<>();

  @JsonIgnore
  @OneToMany(
    mappedBy = "sale",
    cascade = { PERSIST, DETACH, MERGE },
    fetch = LAZY
  )
  private Set<DealNote> dealNote = new HashSet<>();

  @JsonIgnore
  @OneToMany(
    mappedBy = "sale",
    cascade = { PERSIST, DETACH, MERGE },
    fetch = LAZY
  )
  private Set<Contact> contacts = new HashSet<>();

  @JsonIgnore
  @OneToMany(
    mappedBy = "sale",
    cascade = { PERSIST, DETACH, MERGE },
    fetch = LAZY
  )
  private Set<ContactNote> contactNotes = new HashSet<>();

  public Sale() {}

  public Sale(
    String email,
    String status,
    String first_name,
    String last_name,
    String password,
    ApplicationUserRole role
  ) {
    this.email = email;
    this.status = status;
    this.last_name = last_name;
    this.first_name = first_name;
    this.password = password;
    this.role = role;
  }

  public Sale(
    String first_name,
    String last_name,
    String password,
    ApplicationUserRole role
  ) {
    this.last_name = last_name;
    this.first_name = first_name;
    this.password = password;
    this.role = role;
  }

  public String getUsername() {
    if (last_name == null) {
      return first_name;
    }

    return first_name + " " + last_name;
  }

  public List<GrantedAuthority> getUserAuthorities() {
    return role.getGrantedAuthorities();
  }

  public Long getId() {
    return this.id;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStatus() {
    return this.status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getLast_name() {
    return this.last_name;
  }

  public void setLast_name(String last_name) {
    this.last_name = last_name;
  }

  public String getFirst_name() {
    return this.first_name;
  }

  public void setFirst_name(String first_name) {
    this.first_name = first_name;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public ApplicationUserRole getRole() {
    return this.role;
  }

  public void setRole(ApplicationUserRole role) {
    this.role = role;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public Set<Company> getCompanies() {
    return this.companies;
  }

  public void setCompanies(Set<Company> companies) {
    this.companies = companies;
  }

  public Set<Task> getTasks() {
    return this.tasks;
  }

  public void setTasks(Set<Task> tasks) {
    this.tasks = tasks;
  }

  public Set<Deal> getDeals() {
    return this.deals;
  }

  public void setDeals(Set<Deal> deals) {
    this.deals = deals;
  }

  public Set<DealNote> getDealNote() {
    return this.dealNote;
  }

  public void setDealNote(Set<DealNote> dealNote) {
    this.dealNote = dealNote;
  }

  public Set<Contact> getContacts() {
    return this.contacts;
  }

  public void setContacts(Set<Contact> contacts) {
    this.contacts = contacts;
  }

  public Set<ContactNote> getContactNotes() {
    return this.contactNotes;
  }

  public void setContactNotes(Set<ContactNote> contactNotes) {
    this.contactNotes = contactNotes;
  }
}
