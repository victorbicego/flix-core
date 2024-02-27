package com.flix.core.services.general;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AuthenticationResponseDto;
import com.flix.core.models.dtos.UserDto;

public interface AuthenticationService {

  AuthenticationResponseDto authenticate(String username, String password) throws NotFoundException;

  AuthenticationResponseDto register(UserDto userDto) throws NotFoundException;
}
