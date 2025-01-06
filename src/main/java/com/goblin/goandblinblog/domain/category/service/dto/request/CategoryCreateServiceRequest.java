package com.goblin.goandblinblog.domain.category.service.dto.request;

import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;

public record CategoryCreateServiceRequest(
    CategoryType categoryType,
    String title
) {

    public Category toEntity() {
        return Category.create(categoryType, title);
    }
}
