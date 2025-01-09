package com.goblin.goandblinblog.domain.category.service.dto.response;

import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;

public record CategoryResponse(

        Long id,
        CategoryType type,
        String title

) {

    public static CategoryResponse from(Category category) {
        return new CategoryResponse(category.getId(), category.getType(), category.getTitle());
    }

}