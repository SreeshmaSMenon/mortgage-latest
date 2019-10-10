package com.ing.ingmortgage.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="affordability")
@Setter
@Getter
public class Affordability {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long affordabilityId;
  private String maritalStatus;
  private Double affordableAmount;
}
