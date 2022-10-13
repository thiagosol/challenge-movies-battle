package com.thiagosol.moviesbattle.core.gateway;

import com.thiagosol.moviesbattle.core.domain.Game;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;

import java.util.List;

public interface GameGateway {

    Game saveGame(Game game);

    List<Game> findGamesByPlayerLoginAndGameStatus(String login, GameStatus gameStatus);

    List<Game> findAllGames();
}
