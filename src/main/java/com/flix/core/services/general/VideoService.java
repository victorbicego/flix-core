package com.flix.core.services.general;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoWithChannelDto;
import java.util.List;

public interface VideoService {

  List<VideoWithChannelDto> findVideos(
      String channelId, String category, String search, int page, int size);

  VideoWithChannelDto getById(String id) throws NotFoundException;

  List<VideoWithChannelDto> getRelatedVideos(String id) throws NotFoundException;
}
