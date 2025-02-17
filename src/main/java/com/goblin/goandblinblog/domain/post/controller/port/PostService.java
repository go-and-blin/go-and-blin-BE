package com.goblin.goandblinblog.domain.post.controller.port;

import com.goblin.goandblinblog.domain.post.service.dto.request.PostCreateServiceRequest;

public interface PostService {

    String create(Long memberId, PostCreateServiceRequest request);
}