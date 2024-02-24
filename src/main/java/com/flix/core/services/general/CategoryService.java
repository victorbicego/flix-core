package com.flix.core.services.general;

import com.flix.core.models.dtos.CategoryDto;
import java.util.List;

public interface CategoryService {

  List<CategoryDto> getAll();
}
