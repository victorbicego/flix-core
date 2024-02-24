package com.flix.core.models.entities;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "category")
@Data
public class Category {

  @Id private String id;
  @NotNull private String name;
}
