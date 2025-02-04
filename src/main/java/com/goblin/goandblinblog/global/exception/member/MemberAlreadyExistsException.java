package com.goblin.goandblinblog.global.exception.member;

import com.goblin.goandblinblog.global.exception.ErrorCode;
import com.goblin.goandblinblog.global.exception.GoAndBlinException;

public class MemberAlreadyExistsException extends GoAndBlinException {

    public MemberAlreadyExistsException() {
        super(ErrorCode.MEMBER_ALREADY_EXISTS);
    }

}