package com.flix.core.services.general.impl;

import com.flix.core.exceptions.NotFoundException;
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
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserServiceImpl implements UserService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final JwtService jwtService;

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
  public UserDto update(UserDto userDto) throws NotFoundException {
    String username = jwtService.getActiveUsername();
    User foundUser = getByUsername(username);

    foundUser.setName(userDto.getName());
    foundUser.setSurname(userDto.getSurname());

    User updatedUser = userRepository.save(foundUser);
    log.info("User updated successfully. ID: {}", foundUser.getId());
    return userMapper.toDto(updatedUser);
  }

  @Override
  public UserDto updatePassword(UserDto userDto) throws NotFoundException {
    String username = jwtService.getActiveUsername();
    User foundUser = getByUsername(username);
    foundUser.setPassword(userDto.getPassword());
    User updatedUser = userRepository.save(foundUser);
    log.info("Password updated successfully. User with ID: {}", foundUser.getId());
    return userMapper.toDto(updatedUser);
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
