package com.thiagosol.moviesbattle.dataprovider.gateway;

import com.thiagosol.moviesbattle.core.domain.Player;
import com.thiagosol.moviesbattle.core.gateway.PlayerGateway;
import com.thiagosol.moviesbattle.dataprovider.exception.PlayerNotFoundException;
import com.thiagosol.moviesbattle.dataprovider.model.PlayerModel;
import com.thiagosol.moviesbattle.dataprovider.repository.PlayerRepository;
import org.springframework.stereotype.Component;

@Component
public class PlayerGatewayImpl implements PlayerGateway {

    private final PlayerRepository playerRepository;

    public PlayerGatewayImpl(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public Player findPlayerByLogin(String login) {
        return playerRepository.findByLogin(login)
                .map(PlayerModel::toPlayer)
                .orElseThrow(PlayerNotFoundException::new);
    }
}
