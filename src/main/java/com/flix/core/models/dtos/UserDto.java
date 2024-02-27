package com.flix.core.models.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.flix.core.models.Views;
import com.flix.core.models.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {

  private String id;

  @JsonView(Views.Register.class)
  @NotNull
  private String username;

  @JsonView(Views.Register.class)
  @NotNull
  private String password;

  @NotNull private Role role;

  @JsonView(Views.Register.class)
  @NotNull
  private String name;

  @JsonView(Views.Register.class)
  @NotNull
  private String surname;

  @NotNull private boolean isAccountNonExpired;
  @NotNull private boolean isAccountNonLocked;
  @NotNull private boolean isCredentialNonExpired;
  @NotNull private boolean isEnabled;
}
