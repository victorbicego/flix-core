package com.flix.core.services.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.ChannelDto;
import com.flix.core.models.dtos.VideoDto;
import com.flix.core.models.dtos.VideoSyncDto;
import com.flix.core.models.entities.VideoSync;
import com.flix.core.models.mappers.VideoSyncMapper;
import com.flix.core.repositories.VideoSyncRepository;
import com.flix.core.services.admin.ChannelAdminService;
import com.flix.core.services.admin.ProcessVideoSyncAdminService;
import com.flix.core.services.admin.VideoAdminService;
import com.flix.core.services.admin.VideoSyncAdminService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class VideoSyncAdminServiceImpl implements VideoSyncAdminService {

    private final VideoSyncRepository videoSyncRepository;
    private final VideoSyncMapper videoSyncMapper;
    private final VideoAdminService videoAdminService;
    private final ChannelAdminService channelAdminService;
    private final ProcessVideoSyncAdminService processVideoSyncAdminService;

    @Override
    public List<VideoSyncDto> getAll(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return videoSyncRepository.findAll(pageable).stream().map(videoSyncMapper::toDto).toList();
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
    public VideoSyncDto update(String id, VideoSyncDto videoSyncDto) {
        return null;
    }

    //Todo: add schedule for this method
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

    private void processVideosFromChannel(ChannelDto channelDto) throws IOException, NotFoundException {
        String channelOverviewBody =
                getResponseBody("https://www.youtube.com/" + channelDto.getTag() + "/videos");

        Matcher titleMatcher =
                Pattern.compile("\"title\":\\{\"runs\":\\[\\{\"text\":\"([^\"]+)\"")
                        .matcher(channelOverviewBody);
        Matcher videoIdMatcher =
                Pattern.compile("\"watchEndpoint\":\\{\"videoId\":\"([^\"]+)\"")
                        .matcher(channelOverviewBody);

        while (titleMatcher.find() && videoIdMatcher.find()) {
            VideoSync generatedVideoSync =
                    processVideoSyncAdminService.generateSingleVideo(
                            channelDto, titleMatcher.group(1), videoIdMatcher.group(1));
            if (generatedVideoSync != null) {
                saveVideo(generatedVideoSync);
            }
        }
    }

    private void saveVideo(VideoSync videoSync) throws NotFoundException {
        if (isVideoSyncCompleted(videoSync)) {
            videoAdminService.save(mapToVideoDto(videoSync));
        } else {
            videoSyncRepository.save(videoSync);
        }
    }

    private boolean isVideoSyncCompleted(VideoSync videoSync) {
        return (videoSync.getTitle() != null) &&
                (videoSync.getDescription() != null) &&
                (videoSync.getDate() != null) &&
                (videoSync.getLink() != null) &&
                (videoSync.getChannelId() != null) &&
                (videoSync.getCategory() != null);
    }

    private VideoDto mapToVideoDto(VideoSync videoSync) {
        VideoDto videoDto = new VideoDto();
        videoDto.setTitle(videoSync.getTitle());
        videoDto.setLink(videoSync.getLink());
        videoDto.setDescription(videoSync.getDescription());
        videoDto.setDate(videoSync.getDate());
        videoDto.setCategory(videoSync.getCategory());
        videoDto.setChannelId(videoSync.getChannelId());
        return videoDto;
    }

    private String getResponseBody(String url) throws IOException {
        HttpClient httpClient = HttpClients.createDefault();
        HttpGet request = new HttpGet(url);
        HttpResponse response = httpClient.execute(request);
        return EntityUtils.toString(response.getEntity());
    }
}
