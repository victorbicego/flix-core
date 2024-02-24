package com.flix.core.controllers.admin;

import com.flix.core.exceptions.InvalidOperationException;
import com.flix.core.exceptions.NotFoundException;
import com.flix.core.models.dtos.CategoryDto;
import com.flix.core.services.admin.CategoryAdminService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/category")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class CategoryAdminController {

  private final CategoryAdminService categoryAdminService;

  @GetMapping("/get")
  public List<CategoryDto> getAll(
      @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
    return categoryAdminService.getAll(page, size);
  }

  @GetMapping("/get/{id}")
  public CategoryDto getById(@PathVariable String id) throws NotFoundException {
    return categoryAdminService.getById(id);
  }

  @PostMapping("/save")
  public CategoryDto save(@RequestBody CategoryDto categoryDto) {
    return categoryAdminService.save(categoryDto);
  }

  @PutMapping("/update/{id}")
  public CategoryDto update(@PathVariable String id, @RequestBody CategoryDto categoryDto)
      throws NotFoundException {
    return categoryAdminService.update(id, categoryDto);
  }

  @DeleteMapping("/delete/{id}")
  public void deleteById(@PathVariable String id)
      throws NotFoundException, InvalidOperationException {
    categoryAdminService.deleteById(id);
  }
}
