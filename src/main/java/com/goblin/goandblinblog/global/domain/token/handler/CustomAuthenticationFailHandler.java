package com.goblin.goandblinblog.global.domain.token.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblin.goandblinblog.global.exception.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;

import static com.goblin.goandblinblog.global.exception.ErrorCode.INVALID_LOGIN_INPUT;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationFailHandler implements AuthenticationFailureHandler {

    private final ObjectMapper objectMapper;

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        log.info("로그인 실패: {}", exception.getMessage());

        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(SC_UNAUTHORIZED)
                .message(INVALID_LOGIN_INPUT.getMessage())
                .build();
        String errorJsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(errorJsonResponse);
    }

}