package com.flix.core.services.admin;

import java.io.IOException;
import java.util.List;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoSyncDto;

public interface VideoSyncAdminService {
    List<VideoSyncDto> getAll(int page, int size);

    void deleteById(String id) throws NotFoundException;

    VideoSyncDto update(String id, VideoSyncDto videoSyncDto);

    void addMoreVideos() throws IOException, NotFoundException;
}
