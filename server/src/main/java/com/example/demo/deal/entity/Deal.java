package com.example.demo.deal.entity;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.FetchType.LAZY;

import com.example.demo.company.entity.Company;
import com.example.demo.contact.entity.Contact;
import com.example.demo.sale.entity.Sale;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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

  private Instant created_at;
  private Instant updated_at;
  private Instant start_at;

  private String name;
  private Long amount;
  private Integer nb_notes;
  private String type;
  private String stage;

  @Transient
  private List<Long> contact_ids = new ArrayList<>();

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @Column(updatable = false, insertable = false)
  private Long company_id;

  @JsonIgnore
  @ManyToOne(cascade = DETACH, fetch = LAZY)
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @JsonIgnore
  @ManyToOne(cascade = DETACH, fetch = LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  @JsonIgnore
  @ManyToMany(cascade = DETACH, fetch = LAZY)
  @JoinTable(
    name = "deal_contact",
    joinColumns = @JoinColumn(name = "deal_id"),
    inverseJoinColumns = @JoinColumn(name = "contact_id"),
    uniqueConstraints = @UniqueConstraint(
      columnNames = { "deal_id", "contact_id" }
    )
  )
  private List<Contact> contact_list = new ArrayList<>();
}
