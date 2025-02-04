package com.goblin.goandblinblog.global.exception.file;

import com.goblin.goandblinblog.global.exception.ErrorCode;
import com.goblin.goandblinblog.global.exception.GoAndBlinException;

public class InvalidFileExtensionException extends GoAndBlinException {

    public InvalidFileExtensionException() {
        super(ErrorCode.INVALID_FILE_EXTENSION);
    }

}