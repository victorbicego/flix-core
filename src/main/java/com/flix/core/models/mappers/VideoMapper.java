package com.flix.core.models.mappers;

import com.flix.core.models.dtos.VideoDto;
import com.flix.core.models.entities.Video;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VideoMapper {

  private final ModelMapper modelMapper;

  public VideoDto toDto(Video video) {
    return modelMapper.map(video, VideoDto.class);
  }

  public Video toEntity(VideoDto videoDto) {
    return modelMapper.map(videoDto, Video.class);
  }
}
