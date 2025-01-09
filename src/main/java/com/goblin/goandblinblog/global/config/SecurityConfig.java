package com.goblin.goandblinblog.global.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goblin.goandblinblog.global.domain.auth.filter.LoginFilter;
import com.goblin.goandblinblog.global.domain.token.filter.JwtAuthenticationFilter;
import com.goblin.goandblinblog.global.domain.token.handler.CustomAuthenticationFailHandler;
import com.goblin.goandblinblog.global.domain.token.handler.CustomAuthorizationSuccessHandler;
import com.goblin.goandblinblog.global.domain.token.provider.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final ObjectMapper objectMapper;
    private final AuthenticationConfiguration authenticationConfiguration;

    @Bean
    public PasswordEncoder PasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .cors(AbstractHttpConfigurer::disable);

        http
                .authorizeHttpRequests(auth -> {
                    auth
                            // TODO 개발에 따라 수정 필요
                            .requestMatchers("/api/v1/auth/login").permitAll()
                            .anyRequest().permitAll();
                });

        LoginFilter loginFilter = getLoginFilter();

        http
                .addFilterBefore(
                        new JwtAuthenticationFilter(tokenProvider, objectMapper),
                        UsernamePasswordAuthenticationFilter.class
                )
                .addFilterAt(
                        loginFilter,
                        UsernamePasswordAuthenticationFilter.class
                );

        return http.build();
    }

    private LoginFilter getLoginFilter() throws Exception {
        LoginFilter loginFilter = new LoginFilter(authenticationManager(authenticationConfiguration), objectMapper);
        loginFilter.setAuthenticationSuccessHandler(new CustomAuthorizationSuccessHandler(tokenProvider));
        loginFilter.setAuthenticationFailureHandler(new CustomAuthenticationFailHandler(objectMapper));
        return loginFilter;
    }

}