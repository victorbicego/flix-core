package com.flix.core.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.flix.core.models.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdminUpdateUserDto {

    @NotNull
    private String username;
    @NotNull
    private Role role;
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private boolean isAccountNonExpired;
    @NotNull
    private boolean isAccountNonLocked;
    @NotNull
    private boolean isCredentialNonExpired;
    @NotNull
    private boolean isEnabled;
}
