package com.goblin.goandblinblog.global.domain.token.handler;

import com.goblin.goandblinblog.global.domain.token.common.TokenType;
import com.goblin.goandblinblog.global.domain.token.dto.response.TokenResponse;
import com.goblin.goandblinblog.global.domain.token.provider.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthorizationSuccessHandler implements AuthenticationSuccessHandler {

    private final TokenProvider tokenProvider;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        String nickname = user.getUsername();
        TokenResponse tokenResponse = tokenProvider.generateAllToken(nickname);

        response.addHeader(TokenType.AUTHORIZATION_HEADER.getValue(), TokenType.BEARER_PREFIX.getValue() + tokenResponse.accessToken());
    }

}