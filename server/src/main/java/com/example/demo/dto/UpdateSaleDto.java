package com.example.demo.dto;

import com.example.demo.auth.users.ApplicationUserRole;
import lombok.Data;

@Data
public class UpdateSaleDto {

  private Long id;
  private String username;
  private ApplicationUserRole role;
}
