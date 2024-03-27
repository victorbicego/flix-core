package com.flix.core.services.general.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AuthenticationResponseDto;
import com.flix.core.models.dtos.RegisterDto;
import com.flix.core.models.entities.User;
import com.flix.core.models.enums.Role;
import com.flix.core.repositories.UserRepository;
import com.flix.core.services.general.AuthenticationService;
import com.flix.core.services.general.JwtService;
import com.flix.core.services.general.UserService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    @Override
    public AuthenticationResponseDto authenticate(String username, String password)
            throws NotFoundException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        User user = userService.getByUsername(username);
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

}
