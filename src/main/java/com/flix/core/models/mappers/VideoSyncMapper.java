package com.flix.core.models.mappers;

import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flix.core.models.dtos.VideoSyncDto;
import com.flix.core.models.entities.VideoSync;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoSyncMapper {

    private final ModelMapper modelMapper;

    public VideoSyncDto toDto(VideoSync videoSync) {
        return modelMapper.map(videoSync, VideoSyncDto.class);
    }

    public VideoSync toEntity(VideoSyncDto videoSyncDto) {
        return modelMapper.map(videoSyncDto, VideoSync.class);
    }
}
