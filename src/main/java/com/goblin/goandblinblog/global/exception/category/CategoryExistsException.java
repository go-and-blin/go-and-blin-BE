package com.goblin.goandblinblog.global.exception.category;

import com.goblin.goandblinblog.global.exception.ErrorCode;
import com.goblin.goandblinblog.global.exception.GoAndBlinException;

public class CategoryExistsException extends GoAndBlinException {

    public CategoryExistsException() {
        super(ErrorCode.CATEGORY_EXISTS);
    }

}