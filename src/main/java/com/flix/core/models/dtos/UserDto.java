package com.flix.core.models.dtos;

import com.flix.core.models.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

  private String id;
  @NotNull private String username;
  @NotNull private String password;
  @NotNull private Role role;
  @NotNull private String name;
  @NotNull private String surname;
  @NotNull private boolean isAccountNonExpired;
  @NotNull private boolean isAccountNonLocked;
  @NotNull private boolean isCredentialNonExpired;
  @NotNull private boolean isEnabled;
}
