package com.thiagosol.moviesbattle.core.usecase.auth;

import com.thiagosol.moviesbattle.core.domain.Player;
import com.thiagosol.moviesbattle.core.gateway.AuthGateway;
import org.springframework.stereotype.Component;

@Component
public class AuthenticatePlayerUseCase {

    private final AuthGateway authGateway;

    public AuthenticatePlayerUseCase(AuthGateway authGateway) {
        this.authGateway = authGateway;
    }

    public String authenticate(Player player) {
        return authGateway.generateToken(player.getLogin(), player.getPassword());
    }

}
