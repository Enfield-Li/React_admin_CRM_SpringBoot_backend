package com.example.demo.contactNote.entity;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.FetchType.LAZY;

import com.example.demo.contact.entity.Contact;
import com.example.demo.sale.entity.Sale;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ContactNote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @Column(length = 1000)
  private String text;

  private String type;
  private Date date;
  private String status;

  @Column(updatable = false, insertable = false)
  private Long contact_id;

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @JsonIgnore
  @OneToOne(cascade = DETACH, fetch = LAZY)
  @JoinColumn(name = "contact_id")
  private Contact contact;

  @JsonIgnore
  @ManyToOne(cascade = DETACH, fetch = LAZY)
  @JoinColumn(name = "sales_id")
  private Sale sale;
}
