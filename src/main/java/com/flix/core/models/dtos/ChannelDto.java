package com.flix.core.models.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

import com.flix.core.models.enums.Category;

@Data
public class ChannelDto {

  private String id;
  @NotNull private String name;
  @NotNull private String mainLink;
  @NotNull private String logoLink;
  @NotNull private String tag;
  @NotNull private List<Category> categories;
}
