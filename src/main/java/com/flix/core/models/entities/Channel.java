package com.flix.core.models.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "channel")
@Data
public class Channel {

  @Id private String id;
  @NotNull private String name;
  @NotNull private String mainLink;
  @NotNull private String logoLink;
  private String tag;
}
