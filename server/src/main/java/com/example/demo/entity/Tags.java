package com.example.demo.entity;

import static javax.persistence.CascadeType.DETACH;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Tags {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String name;
  private String color;

  @JsonIgnore
  @ManyToMany(cascade = { PERSIST, DETACH }, fetch = LAZY)
  @JoinTable(
    name = "contact_tag",
    joinColumns = @JoinColumn(name = "tag_id"),
    inverseJoinColumns = @JoinColumn(name = "contact_id"),
    uniqueConstraints = @UniqueConstraint(
      columnNames = { "contact_id", "tag_id" }
    )
  )
  private Set<Contact> contacts = new HashSet<>();

  public Tags(String name, String color, Set<Contact> contacts) {
    this.name = name;
    this.color = color;
    this.contacts = contacts;
  }
}
