package com.flix.core.controllers.general;

import com.flix.core.models.dtos.VideoDto;
import com.flix.core.services.general.VideoService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/video")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoController {

  private final VideoService videoService;

  @GetMapping("/find")
  public List<VideoDto> findByWord(
      @RequestParam(defaultValue = "") String word,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return videoService.findByWord(word, page, size);
  }

  @GetMapping("/recent")
  public List<VideoDto> getRecent(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return videoService.getRecent(page, size);
  }

  @GetMapping("/recent/{id}")
  public List<VideoDto> getRecentByCategory(
      @PathVariable String id,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return videoService.getRecentByCategory(id, page, size);
  }
}
