package com.flix.core.services.admin;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoSyncDto;
import java.io.IOException;
import java.util.List;

public interface VideoSyncAdminService {
  List<VideoSyncDto> getAll(int page, int size);

  void deleteById(String id) throws NotFoundException;

  VideoSyncDto update(String id, VideoSyncDto videoSyncDto) throws NotFoundException;

  void addMoreVideos() throws IOException, NotFoundException;
}
