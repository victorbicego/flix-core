package com.flix.core.services.admin;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AdminUpdateUserDto;
import com.flix.core.models.dtos.ChangePasswordDto;
import com.flix.core.models.dtos.UserDto;
import java.util.List;

public interface UserAdminService {

  List<UserDto> getAll(int page, int size);

  UserDto getById(String id) throws NotFoundException;

  UserDto save(UserDto userDto);

  UserDto update(String id, AdminUpdateUserDto adminUpdateUserDto) throws NotFoundException;

  void deleteById(String id) throws NotFoundException;

  UserDto updatePassword(String id, ChangePasswordDto changePasswordDto) throws NotFoundException;
}
