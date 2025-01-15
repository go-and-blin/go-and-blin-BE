package com.goblin.goandblinblog.domain.category.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.tuple;

import com.goblin.goandblinblog.IntegrationTestSupport;
import com.goblin.goandblinblog.domain.category.controller.port.CategoryService;
import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryCreateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryUpdateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.dto.response.CategoryResponse;
import com.goblin.goandblinblog.domain.category.service.port.CategoryRepository;
import com.goblin.goandblinblog.global.exception.category.CategoryExistsException;
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
        CategoryCreateServiceRequest request = createCategoryServiceRequest();

        assertThatNoException().isThrownBy(() -> {
            categoryService.create(request);
        });
    }


    @DisplayName("카테고리 생성 할 때 이미 존재한다면 예외가 발생한다.")
    @Test
    void createCategoryWithExistingCategory() {
        createCategory(CategoryType.ALL, "트러블 슈팅");

        CategoryCreateServiceRequest request = createCategoryServiceRequest();

        assertThatExceptionOfType(CategoryExistsException.class)
                .isThrownBy(() -> categoryService.create(request));

    }

    @DisplayName("카테고리를 수정한다.")
    @Test
    void updateCategory() {
        Category category = createCategory(CategoryType.ALL, "트러블슈팅");
        CategoryUpdateServiceRequest request = new CategoryUpdateServiceRequest("HTTP");

        categoryService.update(category.getId(), request);
        Category result = categoryRepository.findById(category.getId());

        assertThat(result).extracting(
                "id", "type", "title"
        ).contains(
                category.getId(), CategoryType.ALL, "HTTP"
        );

    }

    @DisplayName("카테고리를 1개만 조회한다.")
    @Test
    void getCategory() {
        Category category = createCategory(CategoryType.ALL, "트러블슈팅");

        CategoryResponse categoryResponse = categoryService.getCategory(category.getId());

        assertThat(categoryResponse).extracting(
                "id", "type", "title"
        ).contains(
                category.getId(), CategoryType.ALL, "트러블슈팅"
        );
    }

    @DisplayName("부모 카테고리를 입력하면 해당 자식 카테고리를 전체 조회한다.")
    @Test
    void getCategoriesByCategoryType() {
        CategoryType categoryType = CategoryType.ALL;
        Category category1 = createCategory(categoryType, "스프링");
        Category category2 = createCategory(categoryType, "자바");
        Category category3 = createCategory(categoryType, "파이썬");

        List<CategoryResponse> result = categoryService.getCategoriesByCategoryType(categoryType);

        assertThat(result).extracting(
                        "id", "type", "title")
                .containsExactlyInAnyOrder(
                        tuple(category1.getId(), CategoryType.ALL, "스프링"),
                        tuple(category2.getId(), CategoryType.ALL, "자바"),
                        tuple(category3.getId(), CategoryType.ALL, "파이썬")
                ).hasSize(3);
    }

    private Category createCategory(CategoryType categoryType, String title) {
        return categoryRepository.save(Category.create(categoryType, title));
    }

    private CategoryCreateServiceRequest createCategoryServiceRequest() {
        return new CategoryCreateServiceRequest(
                CategoryType.ALL,
                "트러블 슈팅"
        );
    }
}