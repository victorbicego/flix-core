package com.flix.core.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.flix.core.models.entities.Video;

@Repository
public interface VideoRepository extends MongoRepository<Video, String> {

    Page<Video> findByTitleContainingIgnoreCaseOrderByDateDesc(String word, Pageable pageable);

    Page<Video> findByTitleContainingIgnoreCaseAndCategoryContainingIgnoreCaseOrderByDateDesc(String word, String category, Pageable pageable);

    Page<Video> findByCategoryContainingIgnoreCaseOrderByDateDesc(String word, Pageable pageable);

    Page<Video> findByOrderByDateDesc(Pageable pageable);

    Page<Video> findByChannelIdAndCategoryContainingIgnoreCaseOrderByDateDesc(String channelId, String category, Pageable pageable);

    Page<Video> findByChannelIdOrderByDateDesc(String channelId, Pageable pageable);

    @Query(value = "[ { $match: { channelId: ?0 } }, { $sample: { size: ?1 } } ]")
    List<Video> findRandomVideosByChannelId(String channelId, int count);

    @Query(value = "[ { $match: { category: ?0, channelId: ?1 } }, { $sample: { size: ?2 } } ]")
    List<Video> findRandomVideosByCategoryAndChannelId(String category, String channelId, int count);

    Optional<Video> findByLink(String link);

    void deleteAllByChannelId(String channelId);
}
