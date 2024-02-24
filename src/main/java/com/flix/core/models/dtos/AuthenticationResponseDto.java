package com.flix.core.models.dtos;

import com.flix.core.models.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponseDto {

  private String jwtToken;
  private String username;
  private Role role;
}
