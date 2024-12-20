package com.goblin.goandblinblog.global.exception;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class GoAndBlinException extends RuntimeException {

    private final Map<String, String> validation = new HashMap<>();

    private final ErrorCode errorCode;
}