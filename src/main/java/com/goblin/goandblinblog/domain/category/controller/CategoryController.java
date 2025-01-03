package com.goblin.goandblinblog.domain.category.controller;

import com.goblin.goandblinblog.domain.category.controller.dto.request.CategoryCreateRequest;
import com.goblin.goandblinblog.domain.category.controller.dto.request.CategoryUpdateRequest;
import com.goblin.goandblinblog.domain.category.controller.port.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
    public ResponseEntity<Void> updateCategory(@PathVariable String id, @RequestBody @Valid CategoryUpdateRequest request) {
        categoryService.update(Long.parseLong(id), request.toServiceRequest());
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
