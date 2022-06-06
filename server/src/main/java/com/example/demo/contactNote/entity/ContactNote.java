package com.example.demo.contactNote.entity;

import com.example.demo.contact.entity.Contact;
import com.example.demo.sale.entity.Sale;
import java.time.Instant;
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

  private Instant date;

  @Lob
  @Column(length = 1000)
  private String text;

  private String type;
  private String stats;

  @Column(updatable = false, insertable = false)
  private Long contact_id;

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @OneToOne
  @JoinColumn(name = "contact_id")
  private Contact contact;

  @ManyToOne
  @JoinColumn(name = "sales_id")
  private Sale sale;
}
