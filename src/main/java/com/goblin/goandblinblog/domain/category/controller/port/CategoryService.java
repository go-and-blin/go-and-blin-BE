package com.goblin.goandblinblog.domain.category.controller.port;

import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryUpdateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryCreateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.dto.response.CategoryResponse;

public interface CategoryService {

    void create(CategoryCreateServiceRequest request);

    void update(Long categoryId, CategoryUpdateServiceRequest request);

    CategoryResponse getCategory(Long categoryId);
}
