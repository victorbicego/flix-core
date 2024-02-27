package com.flix.core.controllers.admin;

import com.fasterxml.jackson.annotation.JsonView;
import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.Views;
import com.flix.core.models.dtos.UserDto;
import com.flix.core.services.admin.UserAdminService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserAdminController {

  private final UserAdminService userAdminService;

  @GetMapping("/get")
  public List<UserDto> getAll() {
    return userAdminService.getAll();
  }

  @GetMapping("/get/{id}")
  public UserDto getById(@PathVariable String id) throws NotFoundException {
    return userAdminService.getById(id);
  }

  @PostMapping("/save")
  public UserDto save(@RequestBody @Valid UserDto userDto) {
    return userAdminService.save(userDto);
  }

  @JsonView(Views.AdminUpdateUser.class)
  @PutMapping("/update/{id}")
  public UserDto update(@PathVariable String id, @RequestBody @Valid UserDto userDto)
      throws NotFoundException {
    return userAdminService.update(id, userDto);
  }

  @JsonView(Views.AdminUpdatePassword.class)
  @PutMapping("/update-password/{id}")
  public UserDto updatePassword(@PathVariable String id, @RequestBody @Valid UserDto userDto)
      throws NotFoundException {
    return userAdminService.updatePassword(id, userDto);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteById(@PathVariable String id) throws NotFoundException {
    userAdminService.deleteById(id);
  }
}
