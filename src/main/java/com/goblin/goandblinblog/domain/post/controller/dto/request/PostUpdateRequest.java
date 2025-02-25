package com.goblin.goandblinblog.domain.post.controller.dto.request;

import com.goblin.goandblinblog.domain.post.service.dto.request.PostUpdateServiceRequest;

public record PostUpdateRequest(

        String title,
        String content

) {
    public PostUpdateServiceRequest toService() {
        return new PostUpdateServiceRequest(
                title,
                content
        );
    }
}
