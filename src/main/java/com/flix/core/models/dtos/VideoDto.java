package com.flix.core.models.dtos;

import com.flix.core.models.enums.Category;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VideoDto {

  @NotNull boolean isCategorized;
  private String id;
  @NotNull private String title;
  @NotNull private String link;
  @NotNull private LocalDate date;
  @NotNull private String description;
  @NotNull private String channelId;
  @NotNull private Category category;
}
