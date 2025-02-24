package com.goblin.goandblinblog.domain.post.service.port;

import com.goblin.goandblinblog.domain.post.entity.Post;
import com.goblin.goandblinblog.domain.post.entity.PostPreviewResponse;
import java.util.List;

public interface PostRepository {

    Post save(Post post);

    void deleteAll();

    Post findById(String id);

    void delete(Post post);

    List<PostPreviewResponse> findAll(String lastPostId, Long size);
}