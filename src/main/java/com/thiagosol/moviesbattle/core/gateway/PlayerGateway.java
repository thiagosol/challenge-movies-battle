package com.thiagosol.moviesbattle.core.gateway;

import com.thiagosol.moviesbattle.core.domain.Player;

public interface PlayerGateway {

    Player findPlayerByLogin(String login);

}
