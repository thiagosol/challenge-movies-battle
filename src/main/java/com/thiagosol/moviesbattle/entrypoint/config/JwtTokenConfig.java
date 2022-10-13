package com.thiagosol.moviesbattle.entrypoint.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtTokenConfig {

    private final Long expirationAfter;

    private final String secret;

    public JwtTokenConfig(@Value("${jwt.expiration-after}") Long expirationAfter,
                          @Value("${jwt.secret}") String secret) {
        this.expirationAfter = expirationAfter;
        this.secret = secret;
    }

    public Long getExpirationAfter() {
        return expirationAfter;
    }

    public String getSecret() {
        return secret;
    }

}
