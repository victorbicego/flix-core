package com.flix.core.services.general;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.UserDto;

public interface UserService {
  UserDto getInfo() throws NotFoundException;

  void delete() throws NotFoundException;

  UserDto update(UserDto userDto) throws NotFoundException;

  UserDto updatePassword(UserDto userDto) throws NotFoundException;
}
