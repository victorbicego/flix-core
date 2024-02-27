package com.flix.core.models.dtos;

import com.fasterxml.jackson.annotation.JsonView;
import com.flix.core.models.Views;
import com.flix.core.models.enums.Role;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserDto {

  private String id;

  @JsonView({Views.Register.class, Views.UpdateUser.class, Views.AdminUpdateUser.class})
  @NotNull
  private String username;

  @JsonView({Views.Register.class, Views.UpdatePassword.class, Views.AdminUpdatePassword.class})
  @NotNull
  private String password;

  @JsonView({Views.AdminUpdateUser.class})
  @NotNull
  private Role role;

  @JsonView({Views.Register.class, Views.UpdateUser.class, Views.AdminUpdateUser.class})
  @NotNull
  private String name;

  @JsonView({Views.Register.class, Views.UpdateUser.class, Views.AdminUpdateUser.class})
  @NotNull
  private String surname;

  @JsonView({Views.AdminUpdateUser.class})
  @NotNull
  private boolean isAccountNonExpired;

  @JsonView({Views.AdminUpdateUser.class})
  @NotNull
  private boolean isAccountNonLocked;

  @JsonView({Views.AdminUpdateUser.class})
  @NotNull
  private boolean isCredentialNonExpired;

  @JsonView({Views.AdminUpdateUser.class})
  @NotNull
  private boolean isEnabled;
}
