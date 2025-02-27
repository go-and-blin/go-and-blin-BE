package com.goblin.goandblinblog.domain.post.controller.dto.request;

import com.goblin.goandblinblog.domain.post.service.dto.request.PostCreateServiceRequest;

public record PostCreateRequest(

        String id,
        String title,
        String content,
        String thumbnail,
        Long categoryId

) {

    public PostCreateServiceRequest toService(){
        return new PostCreateServiceRequest(
                id,
                title,
                content,
                thumbnail,
                categoryId
        );
    }
}
