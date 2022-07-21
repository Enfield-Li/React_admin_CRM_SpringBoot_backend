package com.example.demo.entity;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Task {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String type;
  private String text;
  private Date due_date;

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @ToString.Exclude
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @Column(updatable = false, insertable = false)
  private Long contact_id;

  @ToString.Exclude
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "contact_id")
  private Contact contact;
}
