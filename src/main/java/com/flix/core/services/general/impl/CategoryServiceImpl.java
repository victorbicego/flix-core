package com.flix.core.services.general.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.flix.core.models.enums.Category;
import com.flix.core.services.general.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Override
    public List<Category> getAll() {
        return Arrays.asList(Category.values());
    }
}
