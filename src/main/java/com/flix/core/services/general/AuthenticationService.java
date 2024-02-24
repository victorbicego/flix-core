package com.flix.core.services.general;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AuthenticationResponseDto;

public interface AuthenticationService {

  AuthenticationResponseDto authenticate(String username, String password) throws NotFoundException;
}
