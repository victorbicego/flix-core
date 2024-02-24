package com.flix.core.models.mappers;

import com.flix.core.models.dtos.CategoryDto;
import com.flix.core.models.entities.Category;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryMapper {

  private final ModelMapper modelMapper;

  public CategoryDto toDto(Category category) {
    return modelMapper.map(category, CategoryDto.class);
  }

  public Category toEntity(CategoryDto categoryDto) {
    return modelMapper.map(categoryDto, Category.class);
  }
}
