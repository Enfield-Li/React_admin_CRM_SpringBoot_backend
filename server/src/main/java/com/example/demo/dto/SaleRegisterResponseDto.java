package com.example.demo.dto;

import com.example.demo.entity.Sale;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SaleRegisterResponseDto {

  private Sale sale;
  private String error;
}
