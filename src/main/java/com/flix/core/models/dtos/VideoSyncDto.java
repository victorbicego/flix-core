package com.flix.core.models.dtos;

import com.flix.core.models.enums.Category;
import java.time.Duration;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoSyncDto {

  private String id;
  private String title;
  private String link;
  private LocalDate date;
  private String description;
  private String channelId;
  private Category category;
  private Duration duration;
}
