package com.flix.core.services.general.impl;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.models.entities.Channel;
import com.flix.core.models.mappers.ChannelMapper;
import com.flix.core.repositories.ChannelRepository;
import com.flix.core.services.general.ChannelService;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChannelServiceImpl implements ChannelService {

  private final ChannelRepository channelRepository;
  private final ChannelMapper channelMapper;

  @Override
  public ChannelDto findById(String channelId) throws NotFoundException {
    Optional<Channel> optionalChannel = channelRepository.findById(channelId);
    if (optionalChannel.isPresent()) {
      return channelMapper.toDto(optionalChannel.get());
    }
    NotFoundException exception =
        new NotFoundException(String.format("No channel found with ID: %s", channelId));
    log.error("Failed to find channel. Reason: {}", exception.getMessage(), exception);
    throw exception;
  }

  @Override
  public List<ChannelDto> getAll() {
    List<ChannelDto> channelDtoList =
        channelRepository.findAll().stream().map(channelMapper::toDto).collect(Collectors.toList());
    Collections.shuffle(channelDtoList);
    return channelDtoList;
  }
}
