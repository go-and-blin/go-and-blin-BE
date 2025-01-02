package com.goblin.goandblinblog.domain.category.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @Enumerated(EnumType.STRING)
    private CategoryType type;

    @Column(nullable = false)
    private String title;

    @Builder
    private Category(Long id, CategoryType type, String title) {
        this.id = id;
        this.type = type;
        this.title = title;
    }

    public static Category create(CategoryType categoryType, String title) {
        return Category.builder()
            .type(categoryType)
            .title(title)
            .build();
    }
}