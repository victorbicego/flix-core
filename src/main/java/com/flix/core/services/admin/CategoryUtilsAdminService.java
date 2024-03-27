package com.flix.core.services.admin;

import com.flix.core.models.enums.Category;

public interface CategoryUtilsAdminService {
    Category getVideoCategory(String id, String title, String description);
}
