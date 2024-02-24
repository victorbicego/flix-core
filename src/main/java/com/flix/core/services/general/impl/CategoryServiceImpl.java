package com.flix.core.services.general.impl;

import com.flix.core.models.dtos.CategoryDto;
import com.flix.core.models.mappers.CategoryMapper;
import com.flix.core.repositories.CategoryRepository;
import com.flix.core.services.general.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;

  @Override
  public List<CategoryDto> getAll() {
    return categoryRepository.findAll().stream().map(categoryMapper::toDto).toList();
  }
}
