package com.goblin.goandblinblog.domain.category.repository;

import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import com.goblin.goandblinblog.domain.category.service.port.CategoryRepository;
import com.goblin.goandblinblog.global.exception.category.CategoryNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class CategoryRepositoryImpl implements CategoryRepository {

    private final CategoryJpaRepository categoryJpaRepository;

    @Override
    public Category save(Category entity) {
        return categoryJpaRepository.save(entity);
    }

    @Override
    public boolean existsByCategoryTypeAndTitle(CategoryType categoryType, String title) {
        return categoryJpaRepository.existsByTypeAndTitle(categoryType, title);
    }

    @Override
    public Category findById(Long categoryId) {
        return categoryJpaRepository.findById(categoryId)
                .orElseThrow(() -> new CategoryNotFoundException());
    }

}
