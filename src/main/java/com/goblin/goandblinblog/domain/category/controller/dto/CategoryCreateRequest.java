package com.goblin.goandblinblog.domain.category.controller.dto;

import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryCreateServiceRequest;
import jakarta.validation.constraints.NotBlank;

public record CategoryCreateRequest(
        @NotBlank(message = "필수로 선택해야 합니다.")
        CategoryType type,
        @NotBlank(message = "공백은 허용되지 않습니다.")
        String title
) {

    public CategoryCreateServiceRequest toServiceRequest() {
        return new CategoryCreateServiceRequest(type, title);
    }
}
