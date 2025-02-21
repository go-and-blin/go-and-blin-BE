package com.goblin.goandblinblog.domain.post.controller.dto.request;

public record PostCreateRequest(

        String id,
        String title,
        String content,
        Long categoryId

) {
}
