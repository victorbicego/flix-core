package com.flix.core.repositories;

import com.flix.core.models.entities.Video;
import com.flix.core.models.enums.Category;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {

  Page<Video> findByTitleContainingIgnoreCaseOrderByDateDesc(String word, Pageable pageable);

  Page<Video> findOrderByDate(Pageable pageable);

  Page<Video> findByCategoryOrderByDateDesc(Category category, Pageable pageable);

  void deleteAllByChannelId(String channelId);

  Optional<Video> findByLink(String link);
}
