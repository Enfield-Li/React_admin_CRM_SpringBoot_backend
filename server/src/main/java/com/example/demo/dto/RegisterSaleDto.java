package com.example.demo.dto;

import com.example.demo.auth.user.ApplicationUserRole;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RegisterSaleDto {

  @NotNull
  private String username;

  @NotNull
  @Min(value = 4, message = "Password must be longer than 4.")
  private String password;

  @NotNull
  private ApplicationUserRole role;
}
