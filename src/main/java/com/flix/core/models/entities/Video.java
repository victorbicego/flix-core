package com.flix.core.models.entities;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "video")
@Data
public class Video {

  @NotNull boolean isCategorized;
  @Id private String id;
  @NotNull private String title;
  @NotNull private String link;
  @NotNull private LocalDate date;
  private String description;
  @NotNull private String channelId;
  @NotNull private String categoryId;
}
