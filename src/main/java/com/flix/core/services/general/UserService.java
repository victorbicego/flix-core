package com.flix.core.services.general;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChangePasswordDto;
import com.flix.core.models.dtos.UpdateUserDto;
import com.flix.core.models.dtos.UserDto;

public interface UserService {
  UserDto getInfo() throws NotFoundException;

  void delete() throws NotFoundException;

  UserDto update(UpdateUserDto updateUserDto) throws NotFoundException;

  UserDto updatePassword(ChangePasswordDto changePasswordDto) throws NotFoundException;
}
