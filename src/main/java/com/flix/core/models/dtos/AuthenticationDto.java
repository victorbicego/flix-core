package com.flix.core.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AuthenticationDto {

  @NotNull private String username;
  @NotNull private String password;
}
