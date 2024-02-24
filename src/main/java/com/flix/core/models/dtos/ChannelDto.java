package com.flix.core.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ChannelDto {

  private String id;
  @NotNull private String name;
  @NotNull private String mainLink;
  @NotNull private String logoLink;
  private String tag;
}
