package com.goblin.goandblinblog.domain.category.controller;

import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goblin.goandblinblog.ControllerTestSupport;
import com.goblin.goandblinblog.domain.category.controller.dto.request.CategoryCreateRequest;
import com.goblin.goandblinblog.domain.category.controller.dto.request.CategoryUpdateRequest;
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

    @DisplayName("카테고리를 수정한다.")
    @Test
    void updateCategory() throws Exception {
        Long categoryId = 1L;
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest("Test");

        doNothing().when(categoryService).update(categoryId, categoryUpdateRequest.toServiceRequest());

        mockMvc.perform(
                patch("/api/v1/categories/" + categoryId)
                        .content(objectMapper.writeValueAsString(categoryUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isNoContent());
    }

    @DisplayName("카테고리를 수정할 때, 새로운 타이틀값은 필수값이다.")
    @Test
    void updateCategoryWhenEmptyTitle() throws Exception {
        Long categoryId = 1L;
        CategoryUpdateRequest categoryUpdateRequest = new CategoryUpdateRequest("");

        mockMvc.perform(
                patch("/api/v1/categories/" + categoryId)
                        .content(objectMapper.writeValueAsString(categoryUpdateRequest))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.newTitle").value("새 카테고리 이름을 입력해주세요."));
    }
}