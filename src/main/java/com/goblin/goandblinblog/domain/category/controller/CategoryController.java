package com.goblin.goandblinblog.domain.category.controller;

import com.goblin.goandblinblog.domain.category.controller.dto.request.CategoryCreateRequest;
import com.goblin.goandblinblog.domain.category.controller.dto.request.CategoryUpdateRequest;
import com.goblin.goandblinblog.domain.category.controller.port.CategoryService;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import com.goblin.goandblinblog.domain.category.service.dto.response.CategoryResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/categories")
@RestController
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<Void> createCategory(@RequestBody CategoryCreateRequest request) {
        categoryService.create(request.toServiceRequest());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCategory(@PathVariable String id,
        @RequestBody @Valid CategoryUpdateRequest request) {
        categoryService.update(Long.parseLong(id), request.toServiceRequest());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryResponse> getCategory(@PathVariable String id) {
        CategoryResponse category = categoryService.getCategory(Long.parseLong(id));
        return ResponseEntity.status(HttpStatus.OK).body(category);
    }

    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getCategories(
        @RequestParam("type") CategoryType type
    ) {
        List<CategoryResponse> categories = categoryService.getCategoriesByCategoryType(type);
        return ResponseEntity.status(HttpStatus.OK).body(categories);
    }
}
