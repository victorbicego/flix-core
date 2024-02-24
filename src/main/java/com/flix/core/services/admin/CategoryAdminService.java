package com.flix.core.services.admin;

import com.flix.core.exceptions.InvalidOperationException;
import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.CategoryDto;
import java.util.List;

public interface CategoryAdminService {

  List<CategoryDto> getAll(int page, int size);

  CategoryDto getById(String id) throws NotFoundException;

  CategoryDto save(CategoryDto categoryDto);

  CategoryDto update(String id, CategoryDto categoryDto) throws NotFoundException;

  void deleteById(String id) throws NotFoundException, InvalidOperationException;
}
