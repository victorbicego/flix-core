package com.flix.core.models.entities;

import com.flix.core.models.enums.Category;
import java.time.Duration;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "video-sync")
@Data
public class VideoSync {

  @Id private String id;
  private String title;

  @Indexed(unique = true)
  private String link;

  private LocalDate date;
  private String description;
  private String channelId;
  private Category category;
  private Duration duration;
}
