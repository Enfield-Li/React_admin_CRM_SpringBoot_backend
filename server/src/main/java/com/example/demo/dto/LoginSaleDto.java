package com.example.demo.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginSaleDto {

  @NotNull
  private String username;

  @NotNull
  private String password;
}
