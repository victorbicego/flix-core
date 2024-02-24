package com.flix.core.models.dtos;

import com.flix.core.models.enums.Role;
import lombok.Data;

@Data
public class UserDto {

  private String id;
  private String username;
  private String password;
  private Role role;
  private String name;
  private String surname;
  private boolean isAccountNonExpired;
  private boolean isAccountNonLocked;
  private boolean isCredentialNonExpired;
  private boolean isEnabled;
}
