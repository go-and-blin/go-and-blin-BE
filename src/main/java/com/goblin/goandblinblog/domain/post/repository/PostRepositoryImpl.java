package com.goblin.goandblinblog.domain.post.repository;

import com.goblin.goandblinblog.domain.post.entity.Post;
import com.goblin.goandblinblog.domain.post.entity.PostPreviewResponse;
import com.goblin.goandblinblog.domain.post.service.port.PostRepository;
import com.goblin.goandblinblog.global.exception.post.PostNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class PostRepositoryImpl implements PostRepository {

    private final PostJpaRepository postJpaRepository;
    private final PostQueryRepository query;

    @Override
    public Post save(Post post) {
        return postJpaRepository.save(post);
    }

    @Override
    public void deleteAll() {
        postJpaRepository.deleteAll();
    }

    @Override
    public Post findById(String id) {
        return postJpaRepository.findById(id).orElseThrow(PostNotFoundException::new);
    }

    @Override
    public void delete(Post post) {
        postJpaRepository.delete(post);
    }

    @Override
    public List<PostPreviewResponse> findAll(String lastPostId, Long size) {
        return query.findAll(lastPostId, size);
    }
}