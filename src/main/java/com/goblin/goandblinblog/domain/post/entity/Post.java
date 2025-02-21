package com.goblin.goandblinblog.domain.post.entity;

import com.goblin.goandblinblog.domain.category.entity.Category;
import com.goblin.goandblinblog.domain.member.entity.Member;
import com.goblin.goandblinblog.global.domain.base.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Post extends BaseTimeEntity {

    @Id
    @Column(name = "post_id")
    private String id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    public static Post create(
            String id,
            String title,
            String content,
            Member member,
            Category category
    ){
        return Post.builder().
                id(id)
                .title(title)
                .content(content)
                .member(member)
                .category(category)
                .build();
    }

    @Builder
    private Post(String id, Category category, String content, Member member, String title) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.member = member;
        this.category = category;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}