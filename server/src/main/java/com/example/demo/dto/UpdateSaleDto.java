package com.example.demo.dto;

import javax.validation.constraints.NotNull;

import com.example.demo.auth.user.ApplicationUserRole;

import lombok.Data;

@Data
public class UpdateSaleDto {

  private Long id;
  private String username;

  @NotNull
  private ApplicationUserRole role;
}
