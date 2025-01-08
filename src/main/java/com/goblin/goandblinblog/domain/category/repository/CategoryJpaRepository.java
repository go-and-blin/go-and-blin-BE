package com.goblin.goandblinblog.domain.category.repository;

import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.category.entity.CategoryType;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

    boolean existsByTypeAndTitle(CategoryType type, String title);

    List<Category> findAllByType(CategoryType categoryType);
}