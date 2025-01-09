package com.goblin.goandblinblog.global.exception.auth;

import com.goblin.goandblinblog.global.exception.GoAndBlinException;

import static com.goblin.goandblinblog.global.exception.ErrorCode.INVALID_LOGIN_INPUT;

public class InvalidLoginException extends GoAndBlinException {

    public InvalidLoginException() {
        super(INVALID_LOGIN_INPUT);
    }

}