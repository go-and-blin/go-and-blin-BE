package com.goblin.goandblinblog.global.domain.token.dto.response;

import lombok.Builder;

@Builder
public record TokenResponse(

        String accessToken,

        String refreshToken

) {
}
