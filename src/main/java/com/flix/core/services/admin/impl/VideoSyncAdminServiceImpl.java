package com.flix.core.services.admin.impl;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.models.dtos.VideoDto;
import com.flix.core.models.dtos.VideoSyncDto;
import com.flix.core.models.entities.VideoSync;
import com.flix.core.models.mappers.VideoSyncMapper;
import com.flix.core.repositories.VideoSyncRepository;
import com.flix.core.services.admin.*;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoSyncAdminServiceImpl implements VideoSyncAdminService {

  private static final Pattern TITLE_PATTERN =
      Pattern.compile("\"title\":\\{\"runs\":\\[\\{\"text\":\"([^\"]+)\"");
  private static final Pattern VIDEO_ID_PATTERN =
      Pattern.compile("\"watchEndpoint\":\\{\"videoId\":\"([^\"]+)\"");
  private final VideoSyncRepository videoSyncRepository;
  private final VideoSyncMapper videoSyncMapper;
  private final VideoAdminService videoAdminService;
  private final ChannelAdminService channelAdminService;
  private final ProcessVideoSyncAdminService processVideoSyncAdminService;

  @Override
  public List<VideoSyncDto> getAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return videoSyncRepository.findAllByOrderByDateDesc(pageable).stream()
        .map(videoSyncMapper::toDto)
        .toList();
  }

  @Override
  public void deleteById(String id) throws NotFoundException {
    findById(id);
    videoSyncRepository.deleteById(id);
    log.info("Video deleted successfully. ID: {}", id);
  }

  private VideoSync findById(String id) throws NotFoundException {
    Optional<VideoSync> optionalVideoSync = videoSyncRepository.findById(id);
    if (optionalVideoSync.isPresent()) {
      return optionalVideoSync.get();
    }
    NotFoundException exception =
        new NotFoundException(String.format("No video found with ID: %s", id));
    log.error("Failed to find video. Reason: {}", exception.getMessage(), exception);
    throw exception;
  }

  @Override
  public VideoSyncDto update(String id, VideoSyncDto videoSyncDto) throws NotFoundException {
    VideoSync foundVideoSync = findById(id);

    foundVideoSync.setTitle(videoSyncDto.getTitle());
    foundVideoSync.setLink(videoSyncDto.getLink());
    foundVideoSync.setDate(videoSyncDto.getDate());
    foundVideoSync.setDescription(videoSyncDto.getDescription());
    foundVideoSync.setChannelId(videoSyncDto.getChannelId());
    foundVideoSync.setCategory(videoSyncDto.getCategory());
    foundVideoSync.setDuration(videoSyncDto.getDuration());

    if (isVideoSyncCompleted(foundVideoSync)) {
      VideoDto savedVideoDto = videoAdminService.save(mapToVideoDto(foundVideoSync));
      log.info("VideoSync save successfully as Video. ID: {}", savedVideoDto.getId());
      videoSyncRepository.deleteById(foundVideoSync.getId());
      log.info("VideoSync delete successfully. ID: {}", foundVideoSync.getId());
      return mapToVideoSyncDto(savedVideoDto);
    } else {
      VideoSync savedVideoSync = videoSyncRepository.save(foundVideoSync);
      log.info("VideoSync updated successfully. ID: {}", savedVideoSync.getId());
      return videoSyncMapper.toDto(savedVideoSync);
    }
  }

  @Override
  public void addMoreVideos() throws IOException, NotFoundException {
    int page = 0;
    int pageSize = 50;
    boolean lastPage = false;

    while (!lastPage) {
      List<ChannelDto> channelDtoList = channelAdminService.getAll(page, pageSize);
      for (ChannelDto channelDto : channelDtoList) {
        processVideosFromChannel(channelDto);
      }
      if (channelDtoList.size() < pageSize) {
        lastPage = true;
      } else {
        page++;
      }
    }
  }

  private void processVideosFromChannel(ChannelDto channelDto)
      throws IOException, NotFoundException {
    String channelOverviewBody = getResponseBody(createChannelVideosUrl(channelDto.getId()));

    Matcher titleMatcher = TITLE_PATTERN.matcher(channelOverviewBody);
    Matcher videoIdMatcher = VIDEO_ID_PATTERN.matcher(channelOverviewBody);

    while (titleMatcher.find() && videoIdMatcher.find()) {
      String completeVideoLink =
          createCompleteVideoLink(videoIdMatcher.group(1), channelDto.getName());
      if (!isPresentInVideosSync(completeVideoLink)) {
        VideoSync generatedVideoSync =
            processVideoSyncAdminService.generateSingleVideo(
                channelDto, titleMatcher.group(1), videoIdMatcher.group(1));
        if (generatedVideoSync != null) {
          saveVideo(generatedVideoSync);
        }
      }
    }
  }

  private String createChannelVideosUrl(String channelId) {
    return String.format("https://www.youtube.com/%s/videos", channelId);
  }

  private String createCompleteVideoLink(String videoId, String channelName) {
    return String.format(
        "https://www.youtube.com/watch?v=%s&ab_channel=%s", videoId, channelName.replace(" ", "+"));
  }

  private boolean isPresentInVideosSync(String videoLink) {
    return videoSyncRepository.findByLink(videoLink).isPresent();
  }

  private void saveVideo(VideoSync videoSync) throws NotFoundException {
    if (isVideoSyncCompleted(videoSync)) {
      videoAdminService.save(mapToVideoDto(videoSync));
    } else {
      videoSyncRepository.save(videoSync);
    }
  }

  private boolean isVideoSyncCompleted(VideoSync videoSync) {
    return (videoSync.getTitle() != null)
        && (videoSync.getDescription() != null)
        && (videoSync.getDate() != null)
        && (videoSync.getLink() != null)
        && (videoSync.getChannelId() != null)
        && (videoSync.getDuration() != null)
        && (videoSync.getCategory() != null);
  }

  private VideoDto mapToVideoDto(VideoSync videoSync) {
    VideoDto videoDto = new VideoDto();
    videoDto.setId(videoSync.getId());
    videoDto.setTitle(videoSync.getTitle());
    videoDto.setLink(videoSync.getLink());
    videoDto.setDescription(videoSync.getDescription());
    videoDto.setDate(videoSync.getDate());
    videoDto.setCategory(videoSync.getCategory());
    videoDto.setChannelId(videoSync.getChannelId());
    videoDto.setDuration(videoSync.getDuration());
    return videoDto;
  }

  private VideoSyncDto mapToVideoSyncDto(VideoDto videoDto) {
    VideoSyncDto videoSyncDto = new VideoSyncDto();
    videoSyncDto.setId(videoDto.getId());
    videoSyncDto.setTitle(videoDto.getTitle());
    videoSyncDto.setLink(videoDto.getLink());
    videoSyncDto.setDescription(videoDto.getDescription());
    videoSyncDto.setDate(videoDto.getDate());
    videoSyncDto.setCategory(videoDto.getCategory());
    videoSyncDto.setChannelId(videoDto.getChannelId());
    videoSyncDto.setDuration(videoDto.getDuration());
    return videoSyncDto;
  }

  private String getResponseBody(String url) throws IOException {
    HttpClient httpClient = HttpClients.createDefault();
    HttpGet request = new HttpGet(url);
    HttpResponse response = httpClient.execute(request);
    return EntityUtils.toString(response.getEntity());
  }
}
