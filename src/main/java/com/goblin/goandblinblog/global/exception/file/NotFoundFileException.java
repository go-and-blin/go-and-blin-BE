package com.goblin.goandblinblog.global.exception.file;

import com.goblin.goandblinblog.global.exception.ErrorCode;
import com.goblin.goandblinblog.global.exception.GoAndBlinException;

public class NotFoundFileException extends GoAndBlinException {

    public NotFoundFileException() {
        super(ErrorCode.NOT_FOUND_FILE);
    }

}