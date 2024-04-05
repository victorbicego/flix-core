package com.flix.core.controllers.admin;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AdminUpdateUserDto;
import com.flix.core.models.dtos.ChangePasswordDto;
import com.flix.core.models.dtos.UserDto;
import com.flix.core.services.admin.UserAdminService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserAdminController {

  private final UserAdminService userAdminService;

  @GetMapping("/get")
  public List<UserDto> getAll(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return userAdminService.getAll(page, size);
  }

  @GetMapping("/get/{id}")
  public UserDto getById(@PathVariable String id) throws NotFoundException {
    return userAdminService.getById(id);
  }

  @PostMapping("/save")
  public UserDto save(@RequestBody @Valid UserDto userDto) {
    return userAdminService.save(userDto);
  }

  @PutMapping("/update/{id}")
  public UserDto update(
      @PathVariable String id, @RequestBody @Valid AdminUpdateUserDto adminUpdateUserDto)
      throws NotFoundException {
    return userAdminService.update(id, adminUpdateUserDto);
  }

  @PutMapping("/update-password/{id}")
  public UserDto updatePassword(
      @PathVariable String id, @RequestBody @Valid ChangePasswordDto changePasswordDto)
      throws NotFoundException {
    return userAdminService.updatePassword(id, changePasswordDto);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteById(@PathVariable String id) throws NotFoundException {
    userAdminService.deleteById(id);
  }
}
