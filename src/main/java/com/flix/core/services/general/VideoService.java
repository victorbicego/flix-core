package com.flix.core.services.general;

import java.util.List;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoWithChannelDto;

public interface VideoService {

    List<VideoWithChannelDto> findVideos(
            String channelId, String category, String search, int page, int size);

    VideoWithChannelDto getById(String id) throws NotFoundException;

    List<VideoWithChannelDto> getRelatedVideos(String id) throws NotFoundException;
}
