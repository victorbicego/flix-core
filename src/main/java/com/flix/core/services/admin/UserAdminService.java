package com.flix.core.services.admin;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.UserDto;
import java.util.List;

public interface UserAdminService {

  List<UserDto> getAll();

  UserDto getById(String id) throws NotFoundException;

  UserDto save(UserDto userDto);

  UserDto update(String id, UserDto userDto) throws NotFoundException;

  void deleteById(String id) throws NotFoundException;

  UserDto updatePassword(String id, UserDto userDto) throws NotFoundException;
}
