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

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
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
  @OneToMany(mappedBy = "deal", cascade = { DETACH, DETACH }, fetch = LAZY)
  private Set<DealNote> dealNote = new HashSet<>();

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @JsonIgnore
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @Column(updatable = false, insertable = false)
  private Long company_id;

  @JsonIgnore
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
}
