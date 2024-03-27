package com.flix.core.services.general.impl;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoDto;
import com.flix.core.models.dtos.VideoWithChannelDto;
import com.flix.core.models.entities.Video;
import com.flix.core.models.enums.Category;
import com.flix.core.models.mappers.VideoMapper;
import com.flix.core.repositories.VideoRepository;
import com.flix.core.services.general.ChannelService;
import com.flix.core.services.general.VideoService;
import java.util.*;
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
public class VideoServiceImpl implements VideoService {

  private final VideoRepository videoRepository;
  private final VideoMapper videoMapper;
  private final ChannelService channelService;

  @Override
  public List<VideoWithChannelDto> findVideos(
      String channelId, String category, String search, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    if (!channelId.isEmpty() && !channelId.equals("null")) {
      return this.getVideosFromChannel(channelId, category, pageable);
    }
    if (!search.isEmpty() && !search.equals("null")) {
      return this.getVideosFromWord(search, category, pageable);
    }
    return this.getVideosFromCategory(category, pageable);
  }

  private List<VideoWithChannelDto> getVideosFromChannel(
      String channelId, String category, Pageable pageable) {
    Page<Video> videoPage;
    if (category.isEmpty() || Category.valueOf(category).equals(Category.ALL)) {
      videoPage = videoRepository.findByChannelIdOrderByDateDesc(channelId, pageable);
    } else {
      videoPage =
          videoRepository.findByChannelIdAndCategoryContainingIgnoreCaseOrderByDateDesc(
              channelId, category, pageable);
    }
    return mapAllToVideoWithChannelDto(convertToDtoList(videoPage));
  }

  private List<VideoWithChannelDto> getVideosFromWord(
      String word, String category, Pageable pageable) {
    Page<Video> videoPage;
    if (Category.valueOf(category).equals(Category.ALL)) {
      videoPage = videoRepository.findByTitleContainingIgnoreCaseOrderByDateDesc(word, pageable);
    } else {
      videoPage =
          videoRepository
              .findByTitleContainingIgnoreCaseAndCategoryContainingIgnoreCaseOrderByDateDesc(
                  word, category, pageable);
    }
    return mapAllToVideoWithChannelDto(convertToDtoList(videoPage));
  }

  private List<VideoWithChannelDto> getVideosFromCategory(String category, Pageable pageable) {
    Page<Video> videoPage;
    if (category.isEmpty() || Category.valueOf(category).equals(Category.ALL)) {
      videoPage = videoRepository.findByOrderByDateDesc(pageable);
    } else {
      videoPage =
          videoRepository.findByCategoryContainingIgnoreCaseOrderByDateDesc(category, pageable);
    }
    return mapAllToVideoWithChannelDto(convertToDtoList(videoPage));
  }

  private List<VideoWithChannelDto> mapAllToVideoWithChannelDto(List<VideoDto> videoDtoList) {
    return videoDtoList.stream()
        .map(
            videoDto -> {
              try {
                return new VideoWithChannelDto(
                    videoDto, channelService.findById(videoDto.getChannelId()));
              } catch (NotFoundException e) {
                throw new RuntimeException(e);
              }
            })
        .toList();
  }

  @Override
  public VideoWithChannelDto getById(String id) throws NotFoundException {
    Video foundVideo = findById(id);
    VideoDto convertedVideo = videoMapper.toDto(foundVideo);
    return new VideoWithChannelDto(
        convertedVideo, channelService.findById(convertedVideo.getChannelId()));
  }

  @Override
  public List<VideoWithChannelDto> getRelatedVideos(String id) throws NotFoundException {
    Video foundVideo = this.findById(id);
    int numberFromSameCategory = generateRandomNumber(2, 8);
    int numberFromSameChannel = 10 - numberFromSameCategory;

    List<Video> videosFromSameChannel =
        videoRepository.findRandomVideosByChannelId(
            foundVideo.getChannelId(), numberFromSameChannel);
    List<Video> videosFromSameCategory =
        videoRepository.findRandomVideosByCategoryAndChannelId(
            foundVideo.getCategory().toString(), foundVideo.getChannelId(), numberFromSameCategory);

    List<Video> finalList = new ArrayList<>();
    finalList.addAll(videosFromSameChannel);
    finalList.addAll(videosFromSameCategory);

    Collections.shuffle(finalList);

    List<VideoDto> videoDtoList = finalList.stream().map(videoMapper::toDto).toList();

    return mapAllToVideoWithChannelDto(videoDtoList);
  }

  private int generateRandomNumber(int min, int max) {
    Random random = new Random();
    return random.nextInt(max - min + 1) + min;
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

  private List<VideoDto> convertToDtoList(Page<Video> videoPage) {
    return videoPage.getContent().stream().map(videoMapper::toDto).toList();
  }
}
