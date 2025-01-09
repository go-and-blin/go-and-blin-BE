package com.goblin.goandblinblog.domain.category.controller.dto.request;

import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryUpdateServiceRequest;
import jakarta.validation.constraints.NotBlank;

public record CategoryUpdateRequest(

        @NotBlank(message = "새 카테고리 이름을 입력해주세요.")
        String newTitle

) {

    public CategoryUpdateServiceRequest toServiceRequest() {
        return new CategoryUpdateServiceRequest(newTitle);
    }

}