package com.goblin.goandblinblog.domain.post.service.dto.request;

public record PostCreateServiceRequest(

        String uuid,
        String title,
        String content,
        Long categoryId

) {
}
