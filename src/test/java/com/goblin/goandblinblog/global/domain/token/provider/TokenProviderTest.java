package com.goblin.goandblinblog.global.domain.token.provider;

import com.goblin.goandblinblog.IntegrationTestSupport;
import com.goblin.goandblinblog.global.domain.token.dto.response.TokenResponse;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;

import java.lang.reflect.Field;
import java.security.Key;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

class TokenProviderTest extends IntegrationTestSupport {

    private TokenProvider tokenProvider;

    @Value("${spring.jwt.secretKey}")
    private String secretKey;

    @BeforeEach
    void setUp() {
        tokenProvider = new TokenProvider(secretKey);
    }

    @DisplayName("액세스 토큰과 리프레시 토큰을 생성한다.")
    @Test
    void generateAllToken() {
        // given
        Long memberId = 1L;

        // when
        TokenResponse tokenResponse = tokenProvider.generateAllToken(memberId);

        // then
        assertThat(tokenResponse).isNotNull();
        assertThat(tokenResponse.accessToken()).isNotBlank();
        assertThat(tokenResponse.refreshToken()).isNotBlank();
    }

    @DisplayName("액세스 토큰의 subject를 가져온다.")
    @Test
    void getSubjectFromToken() {
        // given
        Long memberId = 1L;
        TokenResponse tokenResponse = tokenProvider.generateAllToken(memberId);

        // when
        String subjectFromToken = tokenProvider.getSubjectFromToken(tokenResponse.accessToken());

        // then
        assertThat(subjectFromToken).isEqualTo(memberId.toString());
    }

    @DisplayName("유효한 토큰을 검증한다.")
    @Test
    void validateTokenValidToken() {
        // given
        Long memberId = 1L;
        TokenResponse tokenResponse = tokenProvider.generateAllToken(memberId);
        String validToken = tokenResponse.accessToken();

        // when
        boolean isValid = tokenProvider.validateToken(validToken);

        // then
        assertThat(isValid).isTrue();
    }

    @DisplayName("유효하지 않은 토큰을 검증한다.")
    @Test
    void validateTokenInvalidToken() {
        // given
        String invalidToken = "invalid.token.value";

        // when
        boolean isValid = tokenProvider.validateToken(invalidToken);

        // then
        assertThat(isValid).isFalse();
    }

    @Test
    @DisplayName("토큰이 만료되었는지 확인한다.")
    void expiredTokenTest() throws Exception {
        // given
        Key key = getDeclaredFieldKey();
        String expiredToken = Jwts.builder()
                .setSubject("test-subject")
                .setExpiration(new Date(System.currentTimeMillis() - 1000))
                .setIssuedAt(new Date(System.currentTimeMillis() - 2000))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();

        // when
        boolean result = tokenProvider.validateToken(expiredToken);

        // then
        assertThat(result).isFalse();
    }

    private Key getDeclaredFieldKey() throws Exception {
        Field keyField = tokenProvider.getClass().getDeclaredField("key");
        keyField.setAccessible(true);
        return (Key) keyField.get(tokenProvider);
    }

}