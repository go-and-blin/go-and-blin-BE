package com.goblin.goandblinblog.domain.auth.controller.dto.request;

public record AuthLoginRequest(

        String nickname,

        String password

) {
}