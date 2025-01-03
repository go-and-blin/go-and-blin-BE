package com.goblin.goandblinblog.domain.category.controller.port;

import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryCreateServiceRequest;

public interface CategoryService {

    void create(CategoryCreateServiceRequest request);
}
