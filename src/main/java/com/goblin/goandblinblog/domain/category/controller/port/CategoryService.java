package com.goblin.goandblinblog.domain.category.controller.port;

import com.goblin.goandblinblog.domain.category.service.dto.CategoryUpdateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryCreateServiceRequest;

public interface CategoryService {

    void create(CategoryCreateServiceRequest request);

    void update(Long categoryId, CategoryUpdateServiceRequest request);
}
