package com.flix.core.controllers.general;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AuthenticationDto;
import com.flix.core.models.dtos.AuthenticationResponseDto;
import com.flix.core.models.dtos.RegisterDto;
import com.flix.core.services.general.AuthenticationService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public AuthenticationResponseDto login(@RequestBody @Valid AuthenticationDto authenticationDto)
            throws NotFoundException {
        return authenticationService.authenticate(
                authenticationDto.getUsername(), authenticationDto.getPassword());
    }

    @PostMapping("/register")
    public AuthenticationResponseDto register(@RequestBody @Valid RegisterDto registerDto)
            throws NotFoundException {
        return authenticationService.register(registerDto);
    }
}
