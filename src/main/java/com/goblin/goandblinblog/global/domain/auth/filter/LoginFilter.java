package com.goblin.goandblinblog.global.domain.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblin.goandblinblog.domain.auth.controller.dto.request.AuthLoginRequest;
import com.goblin.goandblinblog.global.exception.auth.InvalidLoginException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public LoginFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        this.authenticationManager = authenticationManager;
        this.objectMapper = objectMapper;

        setUsernameParameter("nickname");
        setFilterProcessesUrl("/api/v1/auth/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            AuthLoginRequest authLoginRequest = objectMapper.readValue(request.getInputStream(), AuthLoginRequest.class);
            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(authLoginRequest.nickname(), authLoginRequest.password());
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            log.error("LoginFilter.attemptAuthentication :: {}", e.getMessage());
            throw new InvalidLoginException();
        }
    }

}