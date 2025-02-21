package com.goblin.goandblinblog.domain.post.service.dto.request;

public record PostCreateServiceRequest(

        String id,
        String title,
        String content,
        Long categoryId

) {
}
