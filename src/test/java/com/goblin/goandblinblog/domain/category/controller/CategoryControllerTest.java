package com.goblin.goandblinblog.domain.category.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.goblin.goandblinblog.ControllerTestSupport;
import com.goblin.goandblinblog.domain.category.controller.dto.request.CategoryCreateRequest;
import com.goblin.goandblinblog.domain.category.controller.dto.request.CategoryUpdateRequest;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import com.goblin.goandblinblog.domain.category.service.dto.response.CategoryResponse;
import com.goblin.goandblinblog.global.exception.category.CategoryNotFoundException;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

class CategoryControllerTest extends ControllerTestSupport {

    private final String CATEGORY_API = "/api/v1/categories";

    @DisplayName("카테고리를 생성한다.")
    @Test
    void createCategory() throws Exception {
        CategoryCreateRequest request = new CategoryCreateRequest(CategoryType.ALL, "트러블 슈팅");

        mockMvc.perform(
                        post(CATEGORY_API)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @DisplayName("카테고리를 수정한다.")
    @Test
    void updateCategory() throws Exception {
        Long categoryId = 1L;
        CategoryUpdateRequest request = new CategoryUpdateRequest("Test");

        doNothing().when(categoryService)
                .update(categoryId, request.toServiceRequest());

        mockMvc.perform(
                        patch(CATEGORY_API + "/" + categoryId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @DisplayName("카테고리를 수정할 때, 새로운 타이틀값은 필수값이다.")
    @Test
    void updateCategoryWhenEmptyTitle() throws Exception {
        Long categoryId = 1L;
        CategoryUpdateRequest request = new CategoryUpdateRequest("");

        mockMvc.perform(
                        patch(CATEGORY_API + "/" + categoryId)
                                .content(objectMapper.writeValueAsString(request))
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.validation.newTitle").value("새 카테고리 이름을 입력해주세요."));
    }

    @DisplayName("카테고리를 조회한다.")
    @Test
    void getCategory() throws Exception {
        Long categoryId = 1L;
        CategoryResponse response = new CategoryResponse(categoryId, CategoryType.ALL, "트러블 슈팅");

        when(categoryService.getCategory(any(Long.class))).thenReturn(response);

        mockMvc.perform(
                        get(CATEGORY_API + "/" + categoryId)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.type").value("ALL"))
                .andExpect(jsonPath("$.title").value("트러블 슈팅"));

    }

    @DisplayName("카테고리가 존재하지 않는다면, 404 Not Found 발생한다.")
    @Test
    void getCategoryWhenCategoryNotFound() throws Exception {
        Long categoryId = 1L;

        when(categoryService.getCategory(any(Long.class))).thenThrow(new CategoryNotFoundException());

        mockMvc.perform(
                        get(CATEGORY_API + "/" + categoryId)
                )
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @DisplayName("자식 카테고리를 전체 조회한다.")
    @Test
    void getCategoriesByCategoryType() throws Exception {
        Long categoryId = 1L;
        List<CategoryResponse> responses = List.of(new CategoryResponse(categoryId, CategoryType.ALL, "트러블 슈팅"));

        when(categoryService.getCategoriesByCategoryType(any(CategoryType.class))).thenReturn(responses);

        mockMvc.perform(
                        get(CATEGORY_API + "?type=" + "ALL")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(categoryId))
                .andExpect(jsonPath("$[0].type").value("ALL"))
                .andExpect(jsonPath("$[0].title").value("트러블 슈팅"));
    }

    @DisplayName("type 파라미터는 필수값이다.")
    @Test
    void getCategoriesByCategoryTypeWhenTypeParameterIsEmpty() throws Exception {
        mockMvc.perform(
                        get(CATEGORY_API + "?type=" + "")
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("공백은 허용되지 않습니다."));
    }

}