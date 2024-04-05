package com.flix.core.repositories;

import com.flix.core.models.entities.Video;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {

  Page<Video> findAllByOrderByDateDesc(Pageable pageable);

  Optional<Video> findByLink(String link);

  void deleteAllByChannelId(String channelId);
}
