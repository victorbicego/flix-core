package com.flix.core.services.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoDto;
import com.flix.core.models.entities.Video;
import com.flix.core.models.mappers.VideoMapper;
import com.flix.core.repositories.VideoRepository;
import com.flix.core.services.admin.ChannelAdminService;
import com.flix.core.services.admin.VideoAdminService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class VideoAdminServiceImpl implements VideoAdminService {

    private final VideoRepository videoRepository;
    private final VideoMapper videoMapper;
    private final ChannelAdminService channelAdminService;

    @Override
    public List<VideoDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return videoRepository.findAll(pageable).stream().map(videoMapper::toDto).toList();
    }

    @Override
    public VideoDto getById(String id) throws NotFoundException {
        Video foundVideo = findById(id);
        return videoMapper.toDto(foundVideo);
    }

    @Override
    public VideoDto save(VideoDto videoDto) throws NotFoundException {
        channelAdminService.getById(videoDto.getChannelId());
        Video receivedVideo = videoMapper.toEntity(videoDto);
        Video savedVideo = videoRepository.save(receivedVideo);
        log.info("Video saved successfully. ID: {}", savedVideo.getId());
        return videoMapper.toDto(savedVideo);
    }

    @Override
    public VideoDto update(String id, VideoDto videoDto) throws NotFoundException {
        channelAdminService.getById(videoDto.getChannelId());
        Video foundVideo = findById(id);

        foundVideo.setTitle(videoDto.getTitle());
        foundVideo.setLink(videoDto.getLink());
        foundVideo.setDate(videoDto.getDate());
        foundVideo.setDescription(videoDto.getDescription());
        foundVideo.setChannelId(videoDto.getChannelId());
        foundVideo.setCategory(videoDto.getCategory());

        Video savedVideo = videoRepository.save(foundVideo);
        log.info("Video updated successfully. ID: {}", savedVideo.getId());
        return videoMapper.toDto(savedVideo);
    }

    @Override
    public void deleteById(String id) throws NotFoundException {
        findById(id);
        videoRepository.deleteById(id);
        log.info("Video deleted successfully. ID: {}", id);
    }

    @Override
    public boolean isPresent(String videoLinkIncomplete) {
        String completeVideoLink = "https://www.youtube.com/watch?v=" + videoLinkIncomplete;
        return videoRepository.findByLink(completeVideoLink).isPresent();
    }

    private Video findById(String id) throws NotFoundException {
        Optional<Video> optionalVideo = videoRepository.findById(id);
        if (optionalVideo.isPresent()) {
            return optionalVideo.get();
        }
        NotFoundException exception =
                new NotFoundException(String.format("No video found with ID: %s", id));
        log.error("Failed to find video. Reason: {}", exception.getMessage(), exception);
        throw exception;
    }
}
