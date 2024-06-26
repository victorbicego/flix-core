package com.flix.core.controllers.general;

import com.flix.core.models.enums.Category;
import com.flix.core.services.general.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/get")
  public List<Category> getAll() {
    return categoryService.getAll();
  }
}
