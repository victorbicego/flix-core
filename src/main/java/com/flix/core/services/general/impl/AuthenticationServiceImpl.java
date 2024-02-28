package com.flix.core.services.general.impl;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AuthenticationResponseDto;
import com.flix.core.models.dtos.RegisterDto;
import com.flix.core.models.entities.User;
import com.flix.core.models.enums.Role;
import com.flix.core.models.mappers.UserMapper;
import com.flix.core.repositories.UserRepository;
import com.flix.core.services.general.AuthenticationService;
import com.flix.core.services.general.JwtService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;
  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public AuthenticationResponseDto authenticate(String username, String password)
      throws NotFoundException {
    authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    User user = getByUsername(username);
    String jwtToken = jwtService.generateToken(user);
    log.info("User '{}' authenticated successfully.", username);
    return new AuthenticationResponseDto(jwtToken, username, user.getRole());
  }

  @Override
  public AuthenticationResponseDto register(RegisterDto registerDto) throws NotFoundException {
    User newUser = new User();

    newUser.setName(registerDto.getName());
    newUser.setSurname(registerDto.getSurname());
    newUser.setUsername(registerDto.getUsername());
    newUser.setPassword(passwordEncoder.encode(registerDto.getPassword()));
    newUser.setRole(Role.USER);
    newUser.setEnabled(true);
    newUser.setAccountNonExpired(true);
    newUser.setAccountNonLocked(true);
    newUser.setCredentialNonExpired(true);

    User savedUser = userRepository.save(newUser);
    log.info("User '{}' registered successfully.", savedUser.getUsername());
    return authenticate(registerDto.getUsername(), registerDto.getPassword());
  }

  private User getByUsername(String username) throws NotFoundException {
    Optional<User> optionalUser = userRepository.findByUsername(username);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    }
    NotFoundException exception =
        new NotFoundException(String.format("No user found with username: %s", username));
    log.error("Failed to find user. Reason: {}", exception.getMessage(), exception);
    throw exception;
  }
}
