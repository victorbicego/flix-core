package com.flix.core.services.admin;

import com.flix.core.models.enums.Category;
import java.time.Duration;

public interface CategoryUtilsAdminService {
  Category getVideoCategory(String id, String title, String description, Duration duration);
}
