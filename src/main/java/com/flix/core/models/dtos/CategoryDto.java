package com.flix.core.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CategoryDto {

  private String id;
  @NotNull private String name;
}
