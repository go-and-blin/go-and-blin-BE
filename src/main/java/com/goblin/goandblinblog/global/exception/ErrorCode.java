package com.goblin.goandblinblog.global.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCode {

    CATEGORY_EXISTS(409, "이미 존재하는 카테고리입니다."),
    CATEGORY_NOT_FOUND(404, "존재하지 않는 카테고리입니다."),
    INVALID_TOKEN(401, "유효하지 않은 토큰입니다."),
    INVALID_LOGIN_INPUT(400, "로그인 정보가 올바르지 않습니다."),
    MEMBER_NOT_FOUND(404, "존재하지 않는 회원입니다."),
    MEMBER_ALREADY_EXISTS(409, "이미 존재하는 회원입니다."),
    NOT_FOUND_FILE(404, "존재하지 않는 파일입니다."),
    INVALID_FILE_EXTENSION(400, "올바르지 않은 파일 확장자입니다."),
    FAILED_TO_UPLOAD_FILE(500, "파일 업로드에 실패하였습니다."),
    UNAUTHORIZED(401, "인증 정보가 없습니다.");

    private final int status;
    private final String message;

}