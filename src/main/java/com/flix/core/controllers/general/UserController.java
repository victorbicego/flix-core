package com.flix.core.controllers.general;

import com.fasterxml.jackson.annotation.JsonView;
import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.Views;
import com.flix.core.models.dtos.UserDto;
import com.flix.core.services.general.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserController {

  private final UserService userService;

  @GetMapping("/get")
  public UserDto getInfo() throws NotFoundException {
    return userService.getInfo();
  }

  @JsonView(Views.UpdateUser.class)
  @PutMapping("/update")
  public UserDto update(@RequestBody @Valid UserDto userDto) throws NotFoundException {
    return userService.update(userDto);
  }

  @JsonView(Views.UpdatePassword.class)
  @PutMapping("/update-password")
  public UserDto updatePassword(@RequestBody @Valid UserDto userDto) throws NotFoundException {
    return userService.updatePassword(userDto);
  }

  @DeleteMapping("/delete")
  public void delete() throws NotFoundException {
    userService.delete();
  }
}
