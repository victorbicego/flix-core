package com.flix.core.models.entities;

import com.flix.core.models.enums.Category;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "channel")
@Data
public class Channel {

  @Id private String id;
  @NotNull private String name;

  @Indexed(unique = true)
  @NotNull
  private String mainLink;

  @NotNull private String logoLink;

  @Indexed(unique = true)
  @NotNull
  private String tag;

  @NotNull private List<Category> categories;
}
