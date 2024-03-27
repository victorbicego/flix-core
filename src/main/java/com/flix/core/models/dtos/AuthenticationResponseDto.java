package com.flix.core.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.flix.core.models.enums.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {

    private String jwtToken;
    private String username;
    private Role role;
}
