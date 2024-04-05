package com.flix.core.controllers.admin;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoDto;
import com.flix.core.services.admin.VideoAdminService;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/video")
@RequiredArgsConstructor
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
  public VideoDto save(@RequestBody @Valid VideoDto videoDto) throws NotFoundException {
    return videoAdminService.save(videoDto);
  }

  @PutMapping("/update/{id}")
  public VideoDto update(@PathVariable String id, @RequestBody @Valid VideoDto videoDto)
      throws NotFoundException {
    return videoAdminService.update(id, videoDto);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteById(@PathVariable String id) throws NotFoundException {
    videoAdminService.deleteById(id);
  }
}
