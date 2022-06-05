package com.example.demo.dealNote.entity;

import com.example.demo.deal.entity.Deal;
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
import lombok.Data;

@Data
@Entity
public class DealNote {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Lob
  @Column(length = 1000)
  private String text;

  private String type;
  private Instant date;

  @Column(updatable = false, insertable = false)
  private Long sales_id;

  @Column(updatable = false, insertable = false)
  private Long deal_id;

  @ManyToOne
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @OneToOne
  @JoinColumn(name = "deal_id")
  private Deal deal;
}
