package com.flix.core.services.admin;

import java.util.List;

import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.VideoDto;

public interface VideoAdminService {

    List<VideoDto> getAll(int page, int size);

    VideoDto getById(String id) throws NotFoundException;

    VideoDto save(VideoDto videoDto) throws NotFoundException;

    VideoDto update(String id, VideoDto videoDto) throws NotFoundException;

    void deleteById(String id) throws NotFoundException;

    boolean isPresent(String videoLinkIncomplete);
}
