package com.goblin.goandblinblog.domain.post.controller.port;

import com.goblin.goandblinblog.domain.post.service.dto.request.PostCreateServiceRequest;
import com.goblin.goandblinblog.domain.post.service.dto.request.PostUpdateServiceRequest;

public interface PostService {

    String create(Long memberId, PostCreateServiceRequest request);

    String update(String uuid, PostUpdateServiceRequest updateRequest);
}