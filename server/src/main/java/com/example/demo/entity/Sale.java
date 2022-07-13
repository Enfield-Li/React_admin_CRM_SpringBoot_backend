package com.example.demo.entity;

import static com.example.demo.auth.users.ApplicationUserRole.*;
import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

import com.example.demo.auth.users.ApplicationUserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
  private String first_name;

  @JsonIgnore
  @Column(nullable = false)
  private String password;

  // https://vladmihalcea.com/the-best-way-to-map-an-enum-type-with-jpa-and-hibernate/
  @Column(length = 20)
  @Enumerated(EnumType.STRING)
  private ApplicationUserRole role;

  @Transient
  private String username;

  @JsonIgnore
  @OneToMany(mappedBy = "sale", cascade = { PERSIST, DETACH }, fetch = LAZY)
  private Set<Company> companies = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "sale", cascade = { PERSIST, DETACH }, fetch = LAZY)
  private Set<Task> tasks = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "sale", cascade = { PERSIST, DETACH }, fetch = LAZY)
  private Set<Deal> deals = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "sale", cascade = { PERSIST, DETACH }, fetch = LAZY)
  private Set<DealNote> dealNote = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "sale", cascade = { PERSIST, DETACH }, fetch = LAZY)
  private Set<Contact> contacts = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "sale", cascade = { PERSIST, DETACH }, fetch = LAZY)
  private Set<ContactNote> contactNotes = new HashSet<>();

  public String getUsername() {
    if (last_name == null) {
      return first_name;
    }

    return first_name + " " + last_name;
  }

  public List<GrantedAuthority> getUserAuthorities() {
    return role.getGrantedAuthorities();
  }
}
