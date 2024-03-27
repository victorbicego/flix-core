package com.flix.core.services.admin;

import java.util.List;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.AdminUpdateUserDto;
import com.flix.core.models.dtos.ChangePasswordDto;
import com.flix.core.models.dtos.UserDto;

public interface UserAdminService {

    List<UserDto> getAll();

    UserDto getById(String id) throws NotFoundException;

    UserDto save(UserDto userDto);

    UserDto update(String id, AdminUpdateUserDto adminUpdateUserDto) throws NotFoundException;

    void deleteById(String id) throws NotFoundException;

    UserDto updatePassword(String id, ChangePasswordDto changePasswordDto) throws NotFoundException;
}
