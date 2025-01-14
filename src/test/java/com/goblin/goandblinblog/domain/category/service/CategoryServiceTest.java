package com.goblin.goandblinblog.domain.category.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.goblin.goandblinblog.IntegrationTestSupport;
import com.goblin.goandblinblog.domain.category.controller.port.CategoryService;
import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryCreateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryUpdateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.dto.response.CategoryResponse;
import com.goblin.goandblinblog.domain.category.service.port.CategoryRepository;
import com.goblin.goandblinblog.global.exception.ErrorCode;
import com.goblin.goandblinblog.global.exception.category.CategoryExistsException;
import com.goblin.goandblinblog.global.exception.category.CategoryNotFoundException;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CategoryServiceTest extends IntegrationTestSupport {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private CategoryRepository categoryRepository;

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
    }

    @DisplayName("카테고리를 생성한다.")
    @Test
    void createCategory() {
        CategoryCreateServiceRequest request = new CategoryCreateServiceRequest(
            CategoryType.ALL,
            "트러블 슈팅"
        );

        assertDoesNotThrow(() -> {
            categoryService.create(request);
        });
    }


    @DisplayName("카테고리 생성 할 때 이미 존재한다면 예외가 발생한다.")
    @Test
    void createCategoryWithExistingCategory() {
        Category category1 = Category.create(CategoryType.ALL, "트러블 슈팅");
        categoryRepository.save(category1);

        CategoryCreateServiceRequest request = new CategoryCreateServiceRequest(
            CategoryType.ALL,
            "트러블 슈팅"
        );

        CategoryExistsException exception = assertThrows(
            CategoryExistsException.class, () -> {
                categoryService.create(request);
            });

        assertEquals(ErrorCode.CATEGORY_EXISTS.getMessage(), exception.getErrorCode().getMessage());
    }

    @DisplayName("카테고리를 수정한다.")
    @Test
    void updateCategory() {
        Category category1 = Category.create(CategoryType.ALL, "트러블슈팅");
        Category save = categoryRepository.save(category1);
        Long categoryId = save.getId();
        CategoryUpdateServiceRequest request = new CategoryUpdateServiceRequest("HTTP");

        categoryService.update(categoryId, request);

        Category result = categoryRepository.findById(categoryId);

        assertThat(result).extracting(
            "id", "type", "title"
        ).contains(categoryId, CategoryType.ALL, "HTTP");

    }

    @DisplayName("카테고리를 1개만 조회한다.")
    @Test
    void getCategory() {
        Category category1 = Category.create(CategoryType.ALL, "트러블슈팅");
        Category save = categoryRepository.save(category1);
        Long categoryId = save.getId();

        CategoryResponse categoryResponse = categoryService.getCategory(categoryId);

        assertThat(categoryResponse).extracting(
            "id", "type", "title"
        ).contains(
            categoryId, CategoryType.ALL, "트러블슈팅"
        );
    }

    @DisplayName("조회 시 카테고리가 없다면 에외가 발생한다.")
    @Test
    void getCategoryWhenCategoryDoesNotExist() {
        Long categoryId = 1L;

        assertThrows(CategoryNotFoundException.class, () -> {
            categoryService.getCategory(categoryId);
        });
    }

    @DisplayName("부모 카테고리를 받으면 해당 자식 카테고리를 전체 조회한다.")
    @Test
    void getCategoriesByCategoryType() {
        CategoryType categoryType = CategoryType.ALL;
        Category category1 = Category.create(CategoryType.ALL, "스프링");
        Category category2 = Category.create(CategoryType.ALL, "자바");
        Category category3 = Category.create(CategoryType.ALL, "파이썬");

        Category save = categoryRepository.save(category1);
        Category save1 = categoryRepository.save(category2);
        Category save2 = categoryRepository.save(category3);

        List<CategoryResponse> result = categoryService.getCategoriesByCategoryType(categoryType);

        assertThat(result).extracting(
                "id", "type", "title")
            .containsExactlyInAnyOrder(
                tuple(save.getId(), CategoryType.ALL, "스프링"),
                tuple(save1.getId(), CategoryType.ALL, "자바"),
                tuple(save2.getId(), CategoryType.ALL, "파이썬")
            ).hasSize(3);
    }

}