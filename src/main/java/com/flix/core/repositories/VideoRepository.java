package com.flix.core.repositories;

import com.flix.core.models.entities.Video;
import java.util.Optional;

import com.flix.core.models.enums.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {

  Page<Video> findAllByOrderByDateDesc(Pageable pageable);

  Page<Video> findByTitleContainingIgnoreCaseOrderByDateDesc(String word, Pageable pageable);

  Page<Video> findByTitleContainingIgnoreCaseAndCategoryContainingIgnoreCaseOrderByDateDesc(
      String word, String category, Pageable pageable);

  Page<Video> findByCategoryEqualsOrderByDateDesc(Category category, Pageable pageable);

  Page<Video> findByOrderByDateDesc(Pageable pageable);

  Page<Video> findByChannelIdAndCategoryContainingIgnoreCaseOrderByDateDesc(
      String channelId, String category, Pageable pageable);

  Page<Video> findByChannelIdOrderByDateDesc(String channelId, Pageable pageable);

  Optional<Video> findByLink(String link);

  void deleteAllByChannelId(String channelId);
}
