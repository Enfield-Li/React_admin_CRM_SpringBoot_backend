package com.example.demo.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateDealDto {

  @NotNull
  Integer index;

  @NotNull
  String stage;
}
