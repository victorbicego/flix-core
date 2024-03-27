package com.flix.core.services.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AdminUpdateUserDto;
import com.flix.core.models.dtos.ChangePasswordDto;
import com.flix.core.models.dtos.UserDto;
import com.flix.core.models.entities.User;
import com.flix.core.models.mappers.UserMapper;
import com.flix.core.repositories.UserRepository;
import com.flix.core.services.admin.UserAdminService;

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
    public UserDto update(String id, AdminUpdateUserDto adminUpdateUserDto) throws NotFoundException {
        User foundUser = findById(id);

        foundUser.setName(adminUpdateUserDto.getName());
        foundUser.setSurname(adminUpdateUserDto.getSurname());
        foundUser.setUsername(adminUpdateUserDto.getUsername());
        foundUser.setRole(adminUpdateUserDto.getRole());
        foundUser.setEnabled(adminUpdateUserDto.isEnabled());
        foundUser.setAccountNonExpired(adminUpdateUserDto.isAccountNonExpired());
        foundUser.setAccountNonLocked(adminUpdateUserDto.isAccountNonLocked());
        foundUser.setCredentialNonExpired(adminUpdateUserDto.isCredentialNonExpired());

        User savedUser = userRepository.save(foundUser);
        log.info("User updated successfully. ID: {}", savedUser.getId());
        return userMapper.toDto(savedUser);
    }

    @Override
    public UserDto updatePassword(String id, ChangePasswordDto changePasswordDto)
            throws NotFoundException {
        User foundUser = findById(id);

        foundUser.setPassword(passwordEncoder.encode(changePasswordDto.getPassword()));

        User savedUser = userRepository.save(foundUser);
        log.info("Password updated successfully. User with ID: {}", foundUser.getId());
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
        NotFoundException exception =
                new NotFoundException(String.format("No user found with ID: %s", id));
        log.error("Failed to find user. Reason: {}", exception.getMessage(), exception);
        throw exception;
    }
}
