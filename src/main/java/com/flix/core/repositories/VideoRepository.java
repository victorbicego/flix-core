package com.flix.core.repositories;

import com.flix.core.models.entities.Video;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {

  List<Video> findAllByChannelId(String channelId);

  List<Video> findAllByCategoryId(String categoryId);

  Page<Video> findByTitleContainingIgnoreCaseOrderByDateDesc(String word, Pageable pageable);

  Page<Video> findSortByDate(Pageable pageable);

  Page<Video> findByCategoryIdOrderByDateDesc(String id, Pageable pageable);
}
