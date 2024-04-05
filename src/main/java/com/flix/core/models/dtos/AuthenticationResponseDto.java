package com.flix.core.models.dtos;

import com.flix.core.models.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {

  @NotNull private String jwtToken;
  @NotNull private String username;
  @NotNull private Role role;
}
