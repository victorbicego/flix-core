package com.flix.core.models.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.flix.core.models.enums.Category;

@Document(collection = "channel")
@Data
public class Channel {

  @Id private String id;
  @NotNull private String name;
  @NotNull private String mainLink;
  @NotNull private String logoLink;
  @NotNull private String tag;
  @NotNull private List<Category> categories;
}
