package com.flix.core.services.general;

import com.flix.core.models.dtos.VideoDto;
import java.util.List;

public interface VideoService {

  List<VideoDto> findByWord(String word, int page, int size);

  List<VideoDto> getRecent(int page, int size);

  List<VideoDto> getRecentByCategory(String id, int page, int size);
}
