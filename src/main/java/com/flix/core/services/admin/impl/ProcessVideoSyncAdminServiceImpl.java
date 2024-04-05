package com.flix.core.services.admin.impl;

import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.models.entities.VideoSync;
import com.flix.core.models.enums.Category;
import com.flix.core.services.admin.CategoryUtilsAdminService;
import com.flix.core.services.admin.ProcessVideoSyncAdminService;
import com.flix.core.services.admin.VideoAdminService;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProcessVideoSyncAdminServiceImpl implements ProcessVideoSyncAdminService {

  private static final Pattern DURATION_PATTERN =
      Pattern.compile("\"approxDurationMs\":\"(\\d+)\"");
  private static final Pattern DATE_PATTERN =
      Pattern.compile("meta itemprop=\"datePublished\" content=\"([^\"]+)\"");
  private static final Pattern DESCRIPTION_PATTERN =
      Pattern.compile("\"isOwnerViewing\":false,\"shortDescription\":\"([^\"]+)\"");
  private final VideoAdminService videoAdminService;
  private final CategoryUtilsAdminService categoryUtilsAdminService;

  @Override
  public VideoSync generateSingleVideo(ChannelDto channelDto, String title, String videoId)
      throws IOException {
    String completeVideoLink = createCompleteVideoLink(videoId, channelDto.getName());

    if (!videoAdminService.isPresentInVideos(completeVideoLink)) {
      String videoOverviewBody = getResponseBody(completeVideoLink);
      LocalDate date = getVideoDate(videoOverviewBody);
      String description = getVideoDescription(videoOverviewBody);
      Duration duration = getVideoDuration(videoOverviewBody);
      Category category =
          categoryUtilsAdminService.getVideoCategory(
              channelDto.getId(), title, description, duration);
      return createVideo(
          title, completeVideoLink, date, description, channelDto.getId(), category, duration);
    }
    return null;
  }

  private String createCompleteVideoLink(String videoId, String channelName) {
    return String.format(
        "https://www.youtube.com/watch?v=%s&ab_channel=%s", videoId, channelName.replace(" ", "+"));
  }

  private Duration getVideoDuration(String responseBody) {
    Matcher approxDurationMsMatcher = DURATION_PATTERN.matcher(responseBody);
    if (approxDurationMsMatcher.find()) {
      long durationInLongMilliseconds = Long.parseLong(approxDurationMsMatcher.group(1));
      return Duration.ofMillis(durationInLongMilliseconds);
    }
    return null;
  }

  private String getResponseBody(String url) throws IOException {
    HttpClient httpClient = HttpClients.createDefault();
    HttpGet request = new HttpGet(url);
    HttpResponse response = httpClient.execute(request);
    return EntityUtils.toString(response.getEntity());
  }

  private LocalDate getVideoDate(String responseBody) {
    Matcher datePublishedMatcher = DATE_PATTERN.matcher(responseBody);
    if (datePublishedMatcher.find()) {
      LocalDateTime dateTime =
          LocalDateTime.parse(
              datePublishedMatcher.group(1), DateTimeFormatter.ISO_OFFSET_DATE_TIME);
      return dateTime.toLocalDate();
    }
    return null;
  }

  private String getVideoDescription(String responseBody) {
    Matcher descriptionMatcher = DESCRIPTION_PATTERN.matcher(responseBody);
    if (descriptionMatcher.find()) {
      return descriptionMatcher.group(1);
    }
    return null;
  }

  private VideoSync createVideo(
      String title,
      String link,
      LocalDate date,
      String description,
      String channelId,
      Category category,
      Duration duration) {
    VideoSync videoSync = new VideoSync();
    videoSync.setTitle(title);
    videoSync.setLink(link);
    videoSync.setDate(date);
    videoSync.setDescription(description);
    videoSync.setChannelId(channelId);
    videoSync.setCategory(category);
    videoSync.setDuration(duration);
    return videoSync;
  }
}
