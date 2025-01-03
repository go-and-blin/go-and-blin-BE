package com.goblin.goandblinblog.domain.category.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goblin.goandblinblog.ControllerTestSupport;
import com.goblin.goandblinblog.domain.category.controller.dto.CategoryCreateRequest;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class CategoryControllerTest extends ControllerTestSupport {

    @DisplayName("카테고리를 생성한다.")
    @Test
    void createCategory() throws Exception {
        CategoryCreateRequest request = new CategoryCreateRequest(CategoryType.ALL, "트러블 슈팅");

        mockMvc.perform(
                post("/api/v1/categories")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isCreated());
    }
}