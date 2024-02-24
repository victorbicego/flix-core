package com.flix.core.services.general.impl;

import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.models.entities.Channel;
import com.flix.core.models.mappers.ChannelMapper;
import com.flix.core.repositories.ChannelRepository;
import com.flix.core.services.general.ChannelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ChannelServiceImpl implements ChannelService {

  private final ChannelRepository channelRepository;
  private final ChannelMapper channelMapper;

  @Override
  public List<ChannelDto> findByWord(String word, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Channel> channelPage = channelRepository.findByNameContainingIgnoreCase(word, pageable);
    return convertToDtoList(channelPage);
  }

  private List<ChannelDto> convertToDtoList(Page<Channel> channelPage) {
    return channelPage.getContent().stream().map(channelMapper::toDto).toList();
  }
}
