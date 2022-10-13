package com.thiagosol.moviesbattle.core.usecase.game;

import com.thiagosol.moviesbattle.core.domain.Game;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import com.thiagosol.moviesbattle.core.exception.GameInProgressException;
import com.thiagosol.moviesbattle.core.gateway.GameGateway;
import com.thiagosol.moviesbattle.core.gateway.PlayerGateway;
import com.thiagosol.moviesbattle.core.usecase.battle.CreateBattleUseCase;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
public class StartNewGameUseCase {

    private final GameGateway gameGateway;
    private final PlayerGateway playerGateway;
    private final CreateBattleUseCase createBattleUseCase;

    public StartNewGameUseCase(GameGateway gameGateway, PlayerGateway playerGateway, CreateBattleUseCase createBattleUseCase) {
        this.gameGateway = gameGateway;
        this.playerGateway = playerGateway;
        this.createBattleUseCase = createBattleUseCase;
    }

    @Transactional
    public Game execute(String playerLogin){
        validateGameInProgress(playerLogin);
        var player = playerGateway.findPlayerByLogin(playerLogin);
        var newGame = new Game(player);
        newGame.addBattle(createBattleUseCase.execute(newGame));
        return gameGateway.saveGame(newGame);
    }

    private void validateGameInProgress(String playerLogin){
        var gamesInProgress = gameGateway.findGamesByPlayerLoginAndGameStatus(playerLogin, GameStatus.STARTED);
        if(!gamesInProgress.isEmpty()){
            throw new GameInProgressException();
        }
    }

}
