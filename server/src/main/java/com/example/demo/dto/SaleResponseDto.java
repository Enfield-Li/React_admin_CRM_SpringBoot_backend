package com.example.demo.dto;

import com.example.demo.auth.user.ApplicationUser;
import com.example.demo.entity.Sale;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SaleResponseDto {

  @NotNull
  private Long id;

  @NotNull
  private String fullName;

  @NotNull
  private String avatar;

  public SaleResponseDto(ApplicationUser user) {
    this.id = user.getId();
    this.fullName = user.getUsername();
    this.avatar = "https://robohash.org/" + user.getUsername() + ".png";
  }

  public static String toJSON(ApplicationUser user) throws IOException {
    SaleResponseDto dto = new SaleResponseDto(user);
    return new ObjectMapper().writeValueAsString(dto);
  }

  public SaleResponseDto(Sale sale) {
    this.id = sale.getId();
    this.fullName = sale.getUsername();
    this.avatar = "https://robohash.org/" + sale.getUsername() + ".png";
  }
}
