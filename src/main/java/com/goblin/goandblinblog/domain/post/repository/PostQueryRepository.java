package com.goblin.goandblinblog.domain.post.repository;

import static com.goblin.goandblinblog.domain.post.entity.QPost.post;

import com.goblin.goandblinblog.domain.post.entity.PostPreviewResponse;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostQueryRepository {

    private final JPAQueryFactory query;

    public List<PostPreviewResponse> findAll(String lastPostId, Long size) {
        return query.select(
                        Projections.constructor(PostPreviewResponse.class,
                                post.id,
                                post.title,
                                post.member.nickName,
                                post.createTime
                        )
                ).from(post)
                .where(gt(lastPostId))
                .orderBy(post.id.asc())
                .limit(size)
                .fetch();
    }

    private BooleanExpression gt(String lastPostId) {
        if (lastPostId == null) {
            return null;
        }
        return post.id.gt(lastPostId);
    }
}
