package com.flix.core.controllers.general;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoWithChannelDto;
import com.flix.core.services.general.VideoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoController {

  private final VideoService videoService;

  @GetMapping("")
  public List<VideoWithChannelDto> findVideos(
      @RequestParam(defaultValue = "") String channel,
      @RequestParam(defaultValue = "") String category,
      @RequestParam(defaultValue = "") String word,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return videoService.findVideos(channel, category, word, page, size);
  }

  @GetMapping("/{id}")
  public VideoWithChannelDto findById(@PathVariable String id) throws NotFoundException {
    return videoService.getById(id);
  }

  @GetMapping("/related/{id}")
  public List<VideoWithChannelDto> findRelatedVideos(@PathVariable String id)
      throws NotFoundException {
    return videoService.getRelatedVideos(id);
  }
}
