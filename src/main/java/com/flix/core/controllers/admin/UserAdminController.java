package com.flix.core.controllers.admin;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.UserDto;
import com.flix.core.services.admin.UserAdminService;
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
  public UserDto save(@RequestBody UserDto userDto) {
    return userAdminService.save(userDto);
  }

  @PutMapping("/update/{id}")
  public UserDto update(@PathVariable String id, @RequestBody UserDto userDto)
      throws NotFoundException {
    return userAdminService.update(id, userDto);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteById(@PathVariable String id) throws NotFoundException {
    userAdminService.deleteById(id);
  }
}
