package com.goblin.goandblinblog.domain.post.entity;

import java.time.LocalDateTime;

public record PostPreviewResponse(

        String id,
        String title,
        String nickName,
        LocalDateTime createTime

) {
}
