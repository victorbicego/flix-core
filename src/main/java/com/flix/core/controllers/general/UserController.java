package com.flix.core.controllers.general;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChangePasswordDto;
import com.flix.core.models.dtos.UpdateUserDto;
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

  @PutMapping("/update")
  public UserDto update(@RequestBody @Valid UpdateUserDto userDto) throws NotFoundException {
    return userService.update(userDto);
  }

  @PutMapping("/update-password")
  public UserDto updatePassword(@RequestBody @Valid ChangePasswordDto changePasswordDto)
      throws NotFoundException {
    return userService.updatePassword(changePasswordDto);
  }

  @DeleteMapping("/delete")
  public void delete() throws NotFoundException {
    userService.delete();
  }
}
