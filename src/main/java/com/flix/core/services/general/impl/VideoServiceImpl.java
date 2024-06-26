package com.flix.core.services.general.impl;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoDto;
import com.flix.core.models.dtos.VideoWithChannelDto;
import com.flix.core.models.entities.Video;
import com.flix.core.models.mappers.VideoMapper;
import com.flix.core.repositories.VideoRepository;
import com.flix.core.services.general.ChannelService;
import com.flix.core.services.general.VideoService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.SampleOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VideoServiceImpl implements VideoService {

  private final VideoRepository videoRepository;
  private final VideoMapper videoMapper;
  private final ChannelService channelService;
  private final MongoTemplate mongoTemplate;

  @Override
  public List<VideoWithChannelDto> findVideos(
      String channelId, String category, String search, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return mapAllToVideoWithChannelDto(
        convertToDtoList(findVideoByParameters(search, category, channelId, pageable)));
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
    Video foundVideo = findById(id);
    int numberFromSameCategory = generateRandomNumber(3, 9);
    int numberFromSameChannel = 12 - numberFromSameCategory;

    List<Video> videosFromSameChannel =
        findRandomVideosByChannelId(foundVideo.getChannelId(), numberFromSameChannel);
    List<Video> videosFromSameCategory =
        findRandomVideosByCategoryAndExcludingChannelId(
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

  private Page<Video> findVideoByParameters(
      String title, String category, String channelId, Pageable pageable) {
    Criteria criteria = new Criteria();

    if (category.equals("ALL")) {
      category = "";
    }

    if (title != null && !title.isEmpty()) {
      log.info("TITLE");
      criteria = criteria.and("title").regex(title, "i");
    }
    if (!category.isEmpty()) {
      log.info("CATEGORY");
      criteria = criteria.and("category").regex(category, "i");
    }
    if (channelId != null && !channelId.isEmpty()) {
      log.info("CHANNEL");
      criteria = criteria.and("channelId").regex(channelId, "i");
    }

    Query query = Query.query(criteria).with(pageable);
    List<Video> videos = mongoTemplate.find(query, Video.class);
    long count = mongoTemplate.count(Query.query(criteria), Video.class);
    return new PageImpl<>(videos, pageable, count);
  }

  private List<Video> findRandomVideosByCategoryAndExcludingChannelId(
      String category, String channelId, int count) {
    Aggregation aggregation =
        Aggregation.newAggregation(
            Aggregation.match(
                Criteria.where("category").is(category).and("channelId").ne(channelId)),
            Aggregation.sample(count));
    AggregationResults<Video> results = mongoTemplate.aggregate(aggregation, "video", Video.class);
    return results.getMappedResults();
  }

  private List<Video> findRandomVideosByChannelId(String channelId, int count) {
    MatchOperation matchOperation = Aggregation.match(Criteria.where("channelId").is(channelId));
    SampleOperation sampleOperation = Aggregation.sample(count);
    Aggregation aggregation = Aggregation.newAggregation(matchOperation, sampleOperation);
    AggregationResults<Video> results = mongoTemplate.aggregate(aggregation, "video", Video.class);
    return results.getMappedResults();
  }
}
