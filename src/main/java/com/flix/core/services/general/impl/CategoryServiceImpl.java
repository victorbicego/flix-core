package com.flix.core.services.general.impl;

import com.flix.core.models.enums.Category;
import com.flix.core.services.general.CategoryService;

import java.util.Arrays;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

  @Override
  public List<Category> getAll() {
    return Arrays.asList(Category.values());
  }

}
