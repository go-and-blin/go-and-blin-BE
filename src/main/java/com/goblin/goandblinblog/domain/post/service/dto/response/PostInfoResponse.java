package com.goblin.goandblinblog.domain.post.service.dto.response;

import com.goblin.goandblinblog.domain.post.entity.Post;
import java.time.LocalDateTime;

public record PostInfoResponse(

        String id,
        String title,
        String content,
        String nickName,
        LocalDateTime createTime

) {

    public static PostInfoResponse create(Post post) {
        return new PostInfoResponse(
                post.getId(),
                post.getTitle(),
                post.getContent(),
                post.getMember().getNickName(),
                post.getCreateTime()
        );
    }
}
