package com.flix.core.models.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

@Data
public class VideoDto {

  @NotNull boolean isCategorized;
  private String id;
  @NotNull private String title;
  @NotNull private String link;
  @NotNull private LocalDate date;
  private String description;
  @NotNull private String channelId;
  @NotNull private String categoryId;
}
