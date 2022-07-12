package com.example.demo.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginSaleDto {

  @NotNull
  private String username;

  @NotNull
  @Min(value = 4, message = "Password must be longer than 4.")
  private String password;
}
