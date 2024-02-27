package com.flix.core.models.entities;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.flix.core.models.enums.Category;

@Document(collection = "video")
@Data
public class Video {

  @Id private String id;
  @NotNull private String title;
  @NotNull private String link;
  @NotNull private LocalDate date;
  @NotNull private String description;
  @NotNull private String channelId;
  @NotNull private Category category;
  @NotNull boolean isCategorized;
}
