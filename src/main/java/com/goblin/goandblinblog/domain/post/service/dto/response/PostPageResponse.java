package com.goblin.goandblinblog.domain.post.service.dto.response;

import com.goblin.goandblinblog.domain.post.entity.PostPreviewResponse;
import java.util.List;

public record PostPageResponse(

        List<PostPreviewResponse> posts,
        boolean hasNext

) {
}
