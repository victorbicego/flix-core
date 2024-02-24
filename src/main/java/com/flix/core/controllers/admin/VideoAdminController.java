package com.flix.core.controllers.admin;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoDto;
import com.flix.core.services.admin.VideoAdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/video")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoAdminController {

  private final VideoAdminService videoAdminService;

  @GetMapping("/get")
  public List<VideoDto> getAll(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return videoAdminService.getAll(page, size);
  }

  @GetMapping("/get/{id}")
  public VideoDto getById(@PathVariable String id) throws NotFoundException {
    return videoAdminService.getById(id);
  }

  @PostMapping("/save")
  public VideoDto save(@RequestBody VideoDto videoDto) throws NotFoundException {
    return videoAdminService.save(videoDto);
  }

  @PutMapping("/update/{id}")
  public VideoDto update(@PathVariable String id, @RequestBody VideoDto videoDto)
      throws NotFoundException {
    return videoAdminService.update(id, videoDto);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteById(@PathVariable String id) throws NotFoundException {
    videoAdminService.deleteById(id);
  }
}
