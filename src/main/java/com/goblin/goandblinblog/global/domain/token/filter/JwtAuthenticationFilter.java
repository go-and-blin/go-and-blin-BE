package com.goblin.goandblinblog.global.domain.token.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblin.goandblinblog.global.domain.token.provider.TokenProvider;
import com.goblin.goandblinblog.global.exception.dto.ErrorResponse;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.goblin.goandblinblog.global.domain.token.common.TokenType.AUTHORIZATION_HEADER;
import static com.goblin.goandblinblog.global.domain.token.common.TokenType.BEARER_PREFIX;
import static com.goblin.goandblinblog.global.exception.ErrorCode.INVALID_TOKEN;
import static jakarta.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {
        String tokenFromHeader = getTokenFromHeader(request);

        if (tokenFromHeader == null) {
            filterChain.doFilter(request, response);
            return;
        }

        if (!tokenProvider.validateToken(tokenFromHeader)) {
            responseJwtError(response);
            return;
        }

        Authentication authentication = tokenProvider.getAuthentication(tokenFromHeader);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String tokenFromHeader = request.getHeader(AUTHORIZATION_HEADER.getValue());
        if (StringUtils.hasText(tokenFromHeader) && tokenFromHeader.startsWith(BEARER_PREFIX.getValue())) {
            return tokenFromHeader.substring(BEARER_PREFIX.getValue().length());
        }

        return null;
    }

    private void responseJwtError(HttpServletResponse response) throws IOException {
        ErrorResponse errorResponse = ErrorResponse.builder()
                .status(SC_UNAUTHORIZED)
                .message(INVALID_TOKEN.getMessage())
                .build();
        String jsonResponse = objectMapper.writeValueAsString(errorResponse);

        response.setStatus(SC_UNAUTHORIZED);
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonResponse);
    }

}