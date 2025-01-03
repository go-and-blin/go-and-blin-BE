package com.goblin.goandblinblog.domain.category.service;

import com.goblin.goandblinblog.domain.category.controller.port.CategoryService;
import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.service.dto.CategoryUpdateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.dto.request.CategoryCreateServiceRequest;
import com.goblin.goandblinblog.domain.category.service.port.CategoryRepository;
import com.goblin.goandblinblog.global.exception.category.CategoryExistsException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public void create(CategoryCreateServiceRequest request) {
        if(categoryRepository.existsByCategoryTypeAndTitle(request.categoryType(), request.title())){
            throw new CategoryExistsException();
        }
        categoryRepository.save(request.toEntity());
    }

    @Transactional
    @Override
    public void update(Long categoryId, CategoryUpdateServiceRequest request) {
        Category category = categoryRepository.findById(categoryId);
        category.update(request.newTitle());
    }
}
