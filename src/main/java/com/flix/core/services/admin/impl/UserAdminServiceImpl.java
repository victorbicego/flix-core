package com.flix.core.services.admin.impl;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.UserDto;
import com.flix.core.models.entities.User;
import com.flix.core.models.mappers.UserMapper;
import com.flix.core.repositories.UserRepository;
import com.flix.core.services.admin.UserAdminService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class UserAdminServiceImpl implements UserAdminService {

  private final UserRepository userRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Override
  public List<UserDto> getAll() {
    return userRepository.findAll().stream().map(userMapper::toDto).toList();
  }

  @Override
  public UserDto getById(String id) throws NotFoundException {
    User foundUser = findById(id);
    return userMapper.toDto(foundUser);
  }

  @Override
  public UserDto save(UserDto userDto) {
    User receivedUser = userMapper.toEntity(userDto);
    receivedUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    User savedUser = userRepository.save(receivedUser);
    log.info("User saved successfully. ID: {}", savedUser.getId());
    return userMapper.toDto(savedUser);
  }

  @Override
  public UserDto update(String id, UserDto userDto) throws NotFoundException {
    User foundUser = findById(id);

    foundUser.setName(userDto.getName());
    foundUser.setSurname(userDto.getSurname());
    foundUser.setUsername(userDto.getUsername());
    foundUser.setPassword(passwordEncoder.encode(userDto.getPassword()));
    foundUser.setRole(userDto.getRole());
    foundUser.setEnabled(userDto.isEnabled());
    foundUser.setAccountNonExpired(userDto.isAccountNonExpired());
    foundUser.setAccountNonLocked(userDto.isAccountNonLocked());
    foundUser.setCredentialNonExpired(userDto.isCredentialNonExpired());

    User savedUser = userRepository.save(foundUser);
    log.info("User updated successfully. ID: {}", savedUser.getId());
    return userMapper.toDto(savedUser);
  }

  @Override
  public void deleteById(String id) throws NotFoundException {
    findById(id);
    userRepository.deleteById(id);
    log.info("User deleted successfully. ID: {}", id);
  }

  private User findById(String id) throws NotFoundException {
    Optional<User> optionalUser = userRepository.findById(id);
    if (optionalUser.isPresent()) {
      return optionalUser.get();
    }
    NotFoundException exception = new NotFoundException(String.format("No user found with ID: %s", id));
    log.error("Failed to find user. Reason: {}", exception.getMessage(), exception);
    throw exception;
  }
}
