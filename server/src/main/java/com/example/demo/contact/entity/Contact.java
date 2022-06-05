package com.example.demo.contact.entity;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.CascadeType.DETACH;

import com.example.demo.company.entity.Company;
import com.example.demo.sale.entity.Sale;
import com.example.demo.tag.entity.Tag;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import lombok.Data;

@Data
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

  private Instant first_seen;
  private Instant last_seen;
  private Boolean has_newsletter;

  @Transient
  private List<Tag> tags = new ArrayList<>();

  @Column(updatable = false, insertable = false)
  private Long company_id;

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @ManyToOne(cascade = DETACH)
  @JoinColumn(name = "company_id")
  private Company company;

  @ManyToOne(cascade = DETACH)
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @OneToMany(cascade = ALL)
  @JoinColumn(name = "tag_id")
  private List<Tag> tag_list = new ArrayList<>();
}
