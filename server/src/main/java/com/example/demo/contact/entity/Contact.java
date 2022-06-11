package com.example.demo.contact.entity;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.FetchType.LAZY;

import com.example.demo.company.entity.Company;
import com.example.demo.sale.entity.Sale;
import com.example.demo.tag.entity.Tags;
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

  private Instant first_seen;
  private Instant last_seen;
  private Boolean has_newsletter;

  @Transient
  private List<Integer> tags = new ArrayList<>();

  @Transient
  private String raw_tags;

  @Column(updatable = false, insertable = false)
  private Long company_id;

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @JsonIgnore
  @ManyToOne(cascade = DETACH, fetch = LAZY)
  @JoinColumn(name = "company_id")
  private Company company;

  @JsonIgnore
  @ManyToOne(cascade = DETACH, fetch = LAZY)
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @JsonIgnore
  @ManyToMany(cascade = DETACH, fetch = LAZY)
  @JoinTable(
    name = "contact_tag",
    joinColumns = @JoinColumn(name = "contact_id"),
    inverseJoinColumns = @JoinColumn(name = "tag_id"),
    uniqueConstraints = @UniqueConstraint(
      columnNames = { "contact_id", "tag_id" }
    )
  )
  private List<Tags> tag_list = new ArrayList<>();
}
