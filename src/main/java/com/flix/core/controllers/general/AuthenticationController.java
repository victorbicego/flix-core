package com.flix.core.controllers.general;

import com.fasterxml.jackson.annotation.JsonView;
import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.Views;
import com.flix.core.models.dtos.AuthenticationDto;
import com.flix.core.models.dtos.AuthenticationResponseDto;
import com.flix.core.models.dtos.UserDto;
import com.flix.core.services.general.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

  @JsonView(Views.Register.class)
  @PostMapping("/register")
  public AuthenticationResponseDto register(@RequestBody @Valid UserDto userDto)
      throws NotFoundException {
    return authenticationService.register(userDto);
  }
}
