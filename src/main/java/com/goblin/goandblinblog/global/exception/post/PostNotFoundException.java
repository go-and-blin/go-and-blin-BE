package com.goblin.goandblinblog.global.exception.post;

import com.goblin.goandblinblog.global.exception.ErrorCode;
import com.goblin.goandblinblog.global.exception.GoAndBlinException;

public class PostNotFoundException extends GoAndBlinException {
    public PostNotFoundException() {
        super(ErrorCode.POST_NOT_FOUND);
    }
}
