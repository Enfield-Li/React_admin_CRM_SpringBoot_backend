package com.example.demo.dto;

import com.example.demo.auth.users.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import lombok.Data;

@Data
public class SaleResponseDto {

  private Long id;
  private String fullName;
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
}
