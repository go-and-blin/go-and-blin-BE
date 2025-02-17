package com.goblin.goandblinblog.domain.post.controller.dto.request;

public record PostCreateRequest(

        String title,
        String content,
        String test,
        Long categoryId

) {
}
