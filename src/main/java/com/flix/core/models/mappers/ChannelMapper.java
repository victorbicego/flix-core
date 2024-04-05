package com.flix.core.models.mappers;

import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.models.entities.Channel;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChannelMapper {

  private final ModelMapper modelMapper;

  public ChannelDto toDto(Channel channel) {
    return modelMapper.map(channel, ChannelDto.class);
  }

  public Channel toEntity(ChannelDto channelDto) {
    return modelMapper.map(channelDto, Channel.class);
  }
}
