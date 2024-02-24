package com.flix.core.services.general.impl;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AuthenticationResponseDto;
import com.flix.core.models.entities.User;
import com.flix.core.repositories.UserRepository;
import com.flix.core.services.general.AuthenticationService;
import com.flix.core.services.general.JwtService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;

  // Todo: Register User

  @Override
  public AuthenticationResponseDto authenticate(String username, String password)
      throws NotFoundException {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    User user = getByUsername(username);
    String jwtToken = jwtService.generateToken(user);
    return new AuthenticationResponseDto(jwtToken, username, user.getRole());
  }

  private User getByUsername(String username) throws NotFoundException {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    }
    NotFoundException exception = new NotFoundException("No User found with Username: " + username);
    log.error("Error occurred while processing User with Username: " + username, exception);
    throw exception;
  }
}
