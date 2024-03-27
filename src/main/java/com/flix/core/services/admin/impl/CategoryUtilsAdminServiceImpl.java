package com.flix.core.services.admin.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.flix.core.models.enums.Category;
import com.flix.core.services.admin.CategoryUtilsAdminService;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CategoryUtilsAdminServiceImpl implements CategoryUtilsAdminService {

    private static final Map<String, BiFunction<String, String, Category>> FUNCTION_MAP =
            new HashMap<>();

    static {
        FUNCTION_MAP.put("channel1", CategoryUtilsAdminServiceImpl::channel1GetCategory);
        FUNCTION_MAP.put("channel2", CategoryUtilsAdminServiceImpl::channel2GetCategory);
        FUNCTION_MAP.put("channel3", CategoryUtilsAdminServiceImpl::channel3GetCategory);
    }

    private static Category channel1GetCategory(String title, String description) {
        return Category.PODCAST;
    }

    private static Category channel2GetCategory(String title, String description) {
        return Category.PODCAST;
    }

    private static Category channel3GetCategory(String title, String description) {
        return Category.PODCAST;
    }

    @Override
    public Category getVideoCategory(String channelId, String title, String description) {
        BiFunction<String, String, Category> function = FUNCTION_MAP.get(channelId);
        if (function != null) {
            return FUNCTION_MAP.get(channelId).apply(title, description);
        }
        return null;
    }
}
