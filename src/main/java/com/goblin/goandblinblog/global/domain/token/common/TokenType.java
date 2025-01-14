package com.goblin.goandblinblog.global.domain.token.common;

import lombok.Getter;

@Getter
public enum TokenType {

    AUTHORIZATION_HEADER("Authorization"),
    BEARER_PREFIX("Bearer ");

    private final String value;

    TokenType(String value) {
        this.value = value;
    }

}