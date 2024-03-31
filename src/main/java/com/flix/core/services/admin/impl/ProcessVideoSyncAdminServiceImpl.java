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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class ProcessVideoSyncAdminServiceImpl implements ProcessVideoSyncAdminService {

  private final VideoAdminService videoAdminService;
  private final CategoryUtilsAdminService categoryUtilsAdminService;

  @Override
  public VideoSync generateSingleVideo(ChannelDto channelDto, String title, String videoId)
      throws IOException {
    String videoLinkIncomplete = videoId + "&ab_channel=" + channelDto.getName().replace(" ", "+");
    if (!videoAdminService.isPresent(videoLinkIncomplete)) {
      String videoLink = "https://www.youtube.com/watch?v=" + videoLinkIncomplete;
      String videoOverviewBody = getResponseBody(videoLink);
      LocalDate date = getVideoDate(videoOverviewBody);
      String description = getVideoDescription(videoOverviewBody);
      Duration duration = getVideoDuration(videoOverviewBody);
      Category category =
          categoryUtilsAdminService.getVideoCategory(
              channelDto.getId(), title, description, duration);
      return createVideo(
          title, videoLink, date, description, channelDto.getId(), category, duration);
    }
    return null;
  }

  private Duration getVideoDuration(String responseBody) {
    Matcher approxDurationMsMatcher =
        Pattern.compile("\"approxDurationMs\":\"(\\d+)\"").matcher(responseBody);
    if (approxDurationMsMatcher.find()) {
      String durationInMilliseconds = approxDurationMsMatcher.group(1);
      long durationInLongMilliseconds = Long.parseLong(durationInMilliseconds);
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
    Matcher datePublishedMatcher =
        Pattern.compile("meta itemprop=\"datePublished\" content=\"([^\"]+)\"")
            .matcher(responseBody);
    if (datePublishedMatcher.find()) {
      String datePublished = datePublishedMatcher.group(1);
      LocalDateTime dateTime =
          LocalDateTime.parse(datePublished, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
      return dateTime.toLocalDate();
    }
    return null;
  }

  private String getVideoDescription(String responseBody) {
    Matcher descriptionMatcher =
        Pattern.compile("\"isOwnerViewing\":false,\"shortDescription\":\"([^\"]+)\"")
            .matcher(responseBody);
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
