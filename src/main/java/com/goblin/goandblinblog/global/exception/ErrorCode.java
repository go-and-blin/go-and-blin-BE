package com.goblin.goandblinblog.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {
    CATEGORY_EXISTS(409, "이미 존재하는 카테고리입니다."),
    CATEGORY_NOT_FOUND(404, "존재하지 않는 카테고리입니다." );

    private final int status;
    private final String message;
}