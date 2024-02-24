package com.flix.core.controllers.general;

import com.flix.core.models.dtos.CategoryDto;
import com.flix.core.services.general.CategoryService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryController {

  private final CategoryService categoryService;

  @GetMapping("/get")
  public List<CategoryDto> getAll() {
    return categoryService.getAll();
  }
}
