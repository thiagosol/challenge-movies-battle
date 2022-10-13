package com.thiagosol.moviesbattle.dataprovider.gateway;

import com.thiagosol.moviesbattle.core.domain.Game;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import com.thiagosol.moviesbattle.core.gateway.GameGateway;
import com.thiagosol.moviesbattle.dataprovider.model.GameModel;
import com.thiagosol.moviesbattle.dataprovider.repository.GameRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameGatewayImpl implements GameGateway {

    private final GameRepository gameRepository;

    public GameGatewayImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
    }

    @Override
    public Game saveGame(Game game) {
        return gameRepository.save(new GameModel(game)).toGame();
    }

    @Override
    public List<Game> findGamesByPlayerLoginAndGameStatus(String login, GameStatus gameStatus) {
        return gameRepository.findByPlayerLoginAndStatus(login, gameStatus).stream().map(GameModel::toGame).toList();
    }

    @Override
    public List<Game> findAllGames() {
        return gameRepository.findAll().stream().map(GameModel::toGame).toList();
    }

}
