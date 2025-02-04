package com.goblin.goandblinblog.global.exception.auth;

import com.goblin.goandblinblog.global.exception.ErrorCode;
import com.goblin.goandblinblog.global.exception.GoAndBlinException;

public class UnauthorizedException extends GoAndBlinException {

    public UnauthorizedException() {
        super(ErrorCode.UNAUTHORIZED);
    }

}