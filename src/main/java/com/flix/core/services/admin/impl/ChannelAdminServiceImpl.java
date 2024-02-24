package com.flix.core.services.admin.impl;

import com.flix.core.exceptions.InvalidOperationException;
import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.models.entities.Channel;
import com.flix.core.models.entities.Video;
import com.flix.core.models.mappers.ChannelMapper;
import com.flix.core.repositories.ChannelRepository;
import com.flix.core.repositories.VideoRepository;
import com.flix.core.services.admin.ChannelAdminService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ChannelAdminServiceImpl implements ChannelAdminService {

  private final ChannelRepository channelRepository;
  private final ChannelMapper channelMapper;
  private final VideoRepository videoRepository;

  @Override
  public List<ChannelDto> getAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return channelRepository.findAll(pageable).stream().map(channelMapper::toDto).toList();
  }

  @Override
  public ChannelDto getById(String id) throws NotFoundException {
    Channel foundChannel = findById(id);
    return channelMapper.toDto(foundChannel);
  }

  @Override
  public ChannelDto save(ChannelDto channelDto) {
    Channel receivedChannel = channelMapper.toEntity(channelDto);
    Channel savedChannel = channelRepository.save(receivedChannel);
    return channelMapper.toDto(savedChannel);
  }

  @Override
  public ChannelDto update(String id, ChannelDto channelDto) throws NotFoundException {
    Channel convertedChannel = channelMapper.toEntity(channelDto);
    Channel foundChannel = findById(id);

    foundChannel.setName(convertedChannel.getName());
    foundChannel.setTag(convertedChannel.getTag());
    foundChannel.setLogoLink(convertedChannel.getLogoLink());
    foundChannel.setMainLink(convertedChannel.getMainLink());

    Channel savedChannel = channelRepository.save(foundChannel);
    return channelMapper.toDto(savedChannel);
  }

  @Override
  public void deleteById(String id) throws NotFoundException, InvalidOperationException {
    findById(id);
    List<Video> videoList = videoRepository.findAllByChannelId(id);
    if (videoList.size() > 1) {
      channelRepository.deleteById(id);
    } else {
      InvalidOperationException exception =
          new InvalidOperationException(
              "Channel with ID: " + id + " is in use and could not be deleted.");
      log.error("Error occurred while processing Channel with ID: " + id, exception);
      throw exception;
    }
  }

  private Channel findById(String id) throws NotFoundException {
    Optional<Channel> optionalChannel = channelRepository.findById(id);
    if (optionalChannel.isPresent()) {
      return optionalChannel.get();
    }
    NotFoundException exception = new NotFoundException("No Channel found with ID: " + id);
    log.error("Error occurred while processing Channel with ID: " + id, exception);
    throw exception;
  }
}
