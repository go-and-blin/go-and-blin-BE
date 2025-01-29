package com.goblin.goandblinblog.global.exception.file;

import com.goblin.goandblinblog.global.exception.ErrorCode;
import com.goblin.goandblinblog.global.exception.GoAndBlinException;

public class S3FileUploadException extends GoAndBlinException {

    public S3FileUploadException() {
        super(ErrorCode.FAILED_TO_UPLOAD_FILE);
    }

}