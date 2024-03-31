package com.flix.core.controllers.admin;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoSyncDto;
import com.flix.core.services.admin.VideoSyncAdminService;
import jakarta.validation.Valid;
import java.io.IOException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/sync")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class VideoSyncAdminController {

  private final VideoSyncAdminService videoSyncAdminService;

  @GetMapping("/get")
  public List<VideoSyncDto> getAll(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return videoSyncAdminService.getAll(page, size);
  }

  @GetMapping("/add")
  public void getAll() throws IOException, NotFoundException {
    videoSyncAdminService.addMoreVideos();
  }

  @PutMapping("/update/{id}")
  public VideoSyncDto update(@PathVariable String id, @RequestBody @Valid VideoSyncDto videoSyncDto)
      throws NotFoundException {
    return videoSyncAdminService.update(id, videoSyncDto);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteById(@PathVariable String id) throws NotFoundException {
    videoSyncAdminService.deleteById(id);
  }
}
