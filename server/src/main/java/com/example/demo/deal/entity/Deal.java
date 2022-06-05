package com.example.demo.deal.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;

import com.example.demo.company.entity.Company;
import com.example.demo.contact.entity.Contact;
import com.example.demo.sale.entity.Sale;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import lombok.Data;

@Data
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

  private Instant created_at;
  private Instant updated_at;
  private Instant started_at;

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

  @Column(updatable = false, insertable = false)
  private Long contact_id;

  @OneToOne
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @ManyToOne(cascade = DETACH)
  @JoinColumn(name = "company_id")
  private Company company;

  @OneToMany(cascade = ALL)
  @JoinColumn(name = "contact_id")
  private List<Contact> contact_ids_list = new ArrayList<>();
}
