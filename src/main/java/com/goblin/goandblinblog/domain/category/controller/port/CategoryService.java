package com.goblin.goandblinblog.domain.category.controller.port;

import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryUpdateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryCreateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.dto.response.CategoryResponse;
import java.util.List;

public interface CategoryService {

    void create(CategoryCreateServiceRequest request);

    void update(Long categoryId, CategoryUpdateServiceRequest request);

    CategoryResponse getCategory(Long categoryId);

    List<CategoryResponse> getCategoriesByCategoryType(CategoryType categoryType);
}
