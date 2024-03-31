package com.flix.core.repositories;

import com.flix.core.models.entities.Video;
import com.flix.core.models.entities.VideoSync;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoSyncRepository extends MongoRepository<VideoSync, String> {

  Optional<Video> findByLink(String link);

  Page<VideoSync> findAllByOrderByDateDesc(Pageable pageable);
}
