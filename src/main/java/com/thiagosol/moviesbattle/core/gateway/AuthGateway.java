package com.thiagosol.moviesbattle.core.gateway;

public interface AuthGateway {
    String generateToken(String login, String password);
}
