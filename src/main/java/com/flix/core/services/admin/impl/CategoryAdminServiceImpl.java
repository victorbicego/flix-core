package com.flix.core.services.admin.impl;

import com.flix.core.exceptions.InvalidOperationException;
import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.CategoryDto;
import com.flix.core.models.entities.Category;
import com.flix.core.models.entities.Video;
import com.flix.core.models.mappers.CategoryMapper;
import com.flix.core.repositories.CategoryRepository;
import com.flix.core.repositories.VideoRepository;
import com.flix.core.services.admin.CategoryAdminService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CategoryAdminServiceImpl implements CategoryAdminService {

  private final CategoryRepository categoryRepository;
  private final CategoryMapper categoryMapper;
  private final VideoRepository videoRepository;

  @Override
  public List<CategoryDto> getAll(int page, int size) {
    Pageable pageable = PageRequest.of(page, size);
    return categoryRepository.findAll(pageable).stream().map(categoryMapper::toDto).toList();
  }

  @Override
  public CategoryDto getById(String id) throws NotFoundException {
    Category foundCategory = findById(id);
    return categoryMapper.toDto(foundCategory);
  }

  @Override
  public CategoryDto save(CategoryDto categoryDto) {
    Category receivedCategory = categoryMapper.toEntity(categoryDto);
    Category savedCategory = categoryRepository.save(receivedCategory);
    return categoryMapper.toDto(savedCategory);
  }

  @Override
  public CategoryDto update(String id, CategoryDto categoryDto) throws NotFoundException {
    Category convertedCategory = categoryMapper.toEntity(categoryDto);
    Category foundCategory = findById(id);

    foundCategory.setName(convertedCategory.getName());

    Category savedCategory = categoryRepository.save(foundCategory);
    return categoryMapper.toDto(savedCategory);
  }

  @Override
  public void deleteById(String id) throws NotFoundException, InvalidOperationException {
    findById(id);
    List<Video> videoList = videoRepository.findAllByCategoryId(id);
    if (videoList.size() > 1) {
      categoryRepository.deleteById(id);
    } else {
      InvalidOperationException exception =
          new InvalidOperationException(
              "Category with ID: " + id + " is in use and could not be deleted.");
      log.error("Error occurred while processing Category with ID: " + id, exception);
      throw exception;
    }
  }

  private Category findById(String id) throws NotFoundException {
    Optional<Category> optionalCategory = categoryRepository.findById(id);
    if (optionalCategory.isPresent()) {
      return optionalCategory.get();
    }
    NotFoundException exception = new NotFoundException("No Category found with ID: " + id);
    log.error("Error occurred while processing Category with ID: " + id, exception);
    throw exception;
  }
}
