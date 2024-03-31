package com.flix.core.services.general.impl;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChangePasswordDto;
import com.flix.core.models.dtos.UpdateUserDto;
import com.flix.core.models.dtos.UserDto;
import com.flix.core.models.entities.User;
import com.flix.core.models.mappers.UserMapper;
import com.flix.core.repositories.UserRepository;
import com.flix.core.services.general.JwtService;
import com.flix.core.services.general.UserService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final JwtService jwtService;
  private final PasswordEncoder passwordEncoder;

  @Override
  public UserDto getInfo() throws NotFoundException {
    String username = jwtService.getActiveUsername();
    User foundUser = getByUsername(username);
    return userMapper.toDto(foundUser);
  }

  @Override
  public void delete() throws NotFoundException {
    String username = jwtService.getActiveUsername();
    User foundUser = getByUsername(username);
    userRepository.deleteById(foundUser.getId());
    log.info("User deleted successfully. ID: {}", foundUser.getId());
  }

  @Override
  public UserDto update(UpdateUserDto updateUserDto) throws NotFoundException {
    String username = jwtService.getActiveUsername();
    User foundUser = getByUsername(username);

    foundUser.setName(updateUserDto.getName());
    foundUser.setSurname(updateUserDto.getSurname());
    foundUser.setUsername(updateUserDto.getUsername());

    User updatedUser = userRepository.save(foundUser);
    log.info("User updated successfully. ID: {}", foundUser.getId());
    return userMapper.toDto(updatedUser);
  }

  @Override
  public UserDto updatePassword(ChangePasswordDto changePasswordDto) throws NotFoundException {
    String username = jwtService.getActiveUsername();
    User foundUser = getByUsername(username);
    foundUser.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));
    User updatedUser = userRepository.save(foundUser);
    log.info("Password updated successfully. User with ID: {}", foundUser.getId());
    return userMapper.toDto(updatedUser);
  }

  @Override
  public User getByUsername(String username) throws NotFoundException {
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
