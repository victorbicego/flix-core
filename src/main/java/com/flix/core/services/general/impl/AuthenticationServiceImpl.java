package com.flix.core.services.general.impl;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AuthenticationResponseDto;
import com.flix.core.models.dtos.UserDto;
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
  public AuthenticationResponseDto register(UserDto userDto) throws NotFoundException {
    User receivedUser = userMapper.toEntity(userDto);

    receivedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    receivedUser.setRole(Role.USER);
    receivedUser.setEnabled(true);
    receivedUser.setAccountNonExpired(true);
    receivedUser.setAccountNonLocked(true);
    receivedUser.setCredentialNonExpired(true);

    User savedUser = userRepository.save(receivedUser);
    log.info("User '{}' registered successfully.", savedUser.getUsername());
    return authenticate(savedUser.getUsername(), savedUser.getPassword());
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
