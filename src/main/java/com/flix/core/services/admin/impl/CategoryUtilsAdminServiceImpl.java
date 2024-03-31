package com.flix.core.services.admin.impl;

import com.flix.core.models.enums.Category;
import com.flix.core.services.admin.CategoryUtilsAdminService;
import com.flix.core.services.admin.TriFunction;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class CategoryUtilsAdminServiceImpl implements CategoryUtilsAdminService {

  private static final Map<String, TriFunction<String, String, Duration, Category>> FUNCTION_MAP =
      new HashMap<>();

  static {
    FUNCTION_MAP.put("@DunamisMovement_", CategoryUtilsAdminServiceImpl::dunamisMovementGetCategory);
    FUNCTION_MAP.put("@jesus_copy", CategoryUtilsAdminServiceImpl::JesusCopyGetCategory);
    FUNCTION_MAP.put(
        "@PositivamentePodcast", CategoryUtilsAdminServiceImpl::positivamentePodcastGetCategory);
  }

  private static Category dunamisMovementGetCategory(
      String title, String description, Duration duration) {
    if (title.toLowerCase().contains("hangout") && duration.toMinutes() > 20) {
      return Category.PODCAST;
    }

    return null;
  }

  private static Category JesusCopyGetCategory(
      String title, String description, Duration duration) {
    if (title.toLowerCase().contains("podcast jesuscopy") && duration.toMinutes() > 20) {
      return Category.PODCAST;
    }
    return null;
  }

  private static Category positivamentePodcastGetCategory(
      String title, String description, Duration duration) {
    return null;
  }

  @Override
  public Category getVideoCategory(
      String channelId, String title, String description, Duration duration) {
    TriFunction<String, String, Duration, Category> function = FUNCTION_MAP.get(channelId);
    if (function != null) {
      return FUNCTION_MAP.get(channelId).apply(title, description, duration);
    }
    return null;
  }
}
