package com.flix.core.models.dtos;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;

import com.flix.core.models.enums.Category;

@Data
public class VideoDto {

  private String id;
  @NotNull private String title;
  @NotNull private String link;
  @NotNull private LocalDate date;
  @NotNull private String description;
  @NotNull private String channelId;
  @NotNull private Category category;
  @NotNull boolean isCategorized;
}
