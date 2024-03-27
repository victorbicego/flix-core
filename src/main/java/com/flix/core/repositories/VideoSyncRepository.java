package com.flix.core.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.flix.core.models.entities.VideoSync;

@Repository
public interface VideoSyncRepository extends MongoRepository<VideoSync, String> {
}
