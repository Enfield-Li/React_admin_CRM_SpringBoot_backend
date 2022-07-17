package com.example.demo.entity;

import static javax.persistence.FetchType.LAZY;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class DealNote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @Column(length = 1000)
  private String text;

  private String type;
  private Date date;

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @Column(updatable = false, insertable = false)
  private Long deal_id;

  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "deal_id")
  private Deal deal;
}
