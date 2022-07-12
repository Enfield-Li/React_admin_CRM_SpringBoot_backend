package com.example.demo.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginSaleDto {

  @NotNull
  private String username;

  @NotNull
  @Size(min = 4)
  private String password;
}
