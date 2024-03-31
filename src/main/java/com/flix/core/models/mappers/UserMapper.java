package com.flix.core.models.mappers;

import com.flix.core.models.dtos.UserDto;
import com.flix.core.models.entities.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class UserMapper {

  private final ModelMapper modelMapper;

  public UserDto toDto(User user) {
    return modelMapper.map(user, UserDto.class);
  }

  public User toEntity(UserDto userDto) {
    return modelMapper.map(userDto, User.class);
  }
}
