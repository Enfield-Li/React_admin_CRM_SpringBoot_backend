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
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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
  @OneToMany(mappedBy = "contact", fetch = LAZY, cascade = DETACH)
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

  public void addTags(Tags tag) {
    tag_list.add(tag);
  }

  public Contact(
    String first_name,
    String last_name,
    String title,
    String status,
    Date last_seen,
    Company company,
    Sale sale,
    Set<Tags> tag_list,
    String background
  ) {
    this.first_name = first_name;
    this.last_name = last_name;
    this.title = title;
    this.status = status;
    this.last_seen = last_seen;
    this.company = company;
    this.sale = sale;
    this.tag_list = tag_list;
    this.background = background;
  }
}
