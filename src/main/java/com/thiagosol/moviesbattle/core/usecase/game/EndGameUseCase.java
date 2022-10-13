package com.thiagosol.moviesbattle.core.usecase.game;

import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import com.thiagosol.moviesbattle.core.exception.GameInProgressNotFoundException;
import com.thiagosol.moviesbattle.core.gateway.GameGateway;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class EndGameUseCase {

    private final GameGateway gameGateway;

    public EndGameUseCase(GameGateway gameGateway) {
        this.gameGateway = gameGateway;
    }

    @Transactional
    public void execute(String playerLogin){
        var gameInProgress = gameGateway.findGamesByPlayerLoginAndGameStatus(playerLogin, GameStatus.STARTED);

        gameInProgress.stream().findFirst().ifPresentOrElse(game -> {
            game.end();
            gameGateway.saveGame(game);
        }, () -> { throw new GameInProgressNotFoundException(); });
    }

}
