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
import lombok.ToString;

@Entity
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

  @ToString.Exclude
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "sales_id")
  private Sale sale;

  @Column(updatable = false, insertable = false)
  private Long deal_id;

  @ToString.Exclude
  @ManyToOne(fetch = LAZY)
  @JoinColumn(name = "deal_id")
  private Deal deal;

  public DealNote() {}

  public Long getId() {
    return this.id;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getType() {
    return this.type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Long getSales_id() {
    return this.sales_id;
  }

  public void setSales_id(Long sales_id) {
    this.sales_id = sales_id;
  }

  public Sale getSale() {
    return this.sale;
  }

  public void setSale(Sale sale) {
    this.sale = sale;
  }

  public Long getDeal_id() {
    return this.deal_id;
  }

  public void setDeal_id(Long deal_id) {
    this.deal_id = deal_id;
  }

  public Deal getDeal() {
    return this.deal;
  }

  public void setDeal(Deal deal) {
    this.deal = deal;
  }
}
