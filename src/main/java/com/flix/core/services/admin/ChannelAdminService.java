package com.flix.core.services.admin;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChannelDto;
import java.util.List;

public interface ChannelAdminService {

  List<ChannelDto> getAll(int page, int size);

  ChannelDto getById(String id) throws NotFoundException;

  ChannelDto save(ChannelDto channelDto);

  ChannelDto update(String id, ChannelDto channelDto) throws NotFoundException;

  void deleteById(String id) throws NotFoundException;
}
