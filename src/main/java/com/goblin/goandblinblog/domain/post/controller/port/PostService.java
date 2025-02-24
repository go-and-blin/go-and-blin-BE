package com.goblin.goandblinblog.domain.post.controller.port;

import com.goblin.goandblinblog.domain.post.service.dto.request.PostCreateServiceRequest;
import com.goblin.goandblinblog.domain.post.service.dto.request.PostUpdateServiceRequest;
import com.goblin.goandblinblog.domain.post.entity.PostPreviewResponse;
import com.goblin.goandblinblog.domain.post.service.dto.response.PostPageResponse;
import java.util.List;

public interface PostService {

    String create(Long memberId, PostCreateServiceRequest request);

    String update(String id, PostUpdateServiceRequest updateRequest);

    void delete(String id);

    PostPageResponse findAll(String lastPostId, Long size);
}