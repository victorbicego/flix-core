package com.flix.core.services.general.impl;

import com.flix.core.models.dtos.VideoDto;
import com.flix.core.models.entities.Video;
import com.flix.core.models.mappers.VideoMapper;
import com.flix.core.repositories.VideoRepository;
import com.flix.core.services.general.VideoService;
import java.util.List;
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

  @Override
  public List<VideoDto> findByWord(String word, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Video> videoPage =
        videoRepository.findByTitleContainingIgnoreCaseOrderByDateDesc(word, pageable);
    return convertToDtoList(videoPage);
  }

  @Override
  public List<VideoDto> getRecent(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Video> videoPage = videoRepository.findSortByDate(pageable);
    return convertToDtoList(videoPage);
  }

  @Override
  public List<VideoDto> getRecentByCategory(String id, int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    Page<Video> videoPage = videoRepository.findByCategoryIdOrderByDateDesc(id, pageable);
    return convertToDtoList(videoPage);
  }

  private List<VideoDto> convertToDtoList(Page<Video> videoPage) {
    return videoPage.getContent().stream().map(videoMapper::toDto).toList();
  }
}
