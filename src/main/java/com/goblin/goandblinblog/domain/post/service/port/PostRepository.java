package com.goblin.goandblinblog.domain.post.service.port;

import com.goblin.goandblinblog.domain.post.entity.Post;

public interface PostRepository {

    Post save(Post post);

    void deleteAll();
}