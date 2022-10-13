package com.thiagosol.moviesbattle.core.usecase.game;

import com.thiagosol.moviesbattle.core.domain.Game;
import com.thiagosol.moviesbattle.core.domain.Player;
import com.thiagosol.moviesbattle.core.domain.Ranking;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import com.thiagosol.moviesbattle.core.exception.GameInProgressException;
import com.thiagosol.moviesbattle.core.gateway.GameGateway;
import com.thiagosol.moviesbattle.core.gateway.PlayerGateway;
import com.thiagosol.moviesbattle.core.usecase.battle.CreateBattleUseCase;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.List;

@Component
public class GetRankingUseCase {

    private static final int SCALE = 2;
    private final GameGateway gameGateway;
    private final PlayerGateway playerGateway;
    private final CreateBattleUseCase createBattleUseCase;

    public GetRankingUseCase(GameGateway gameGateway, PlayerGateway playerGateway, CreateBattleUseCase createBattleUseCase) {
        this.gameGateway = gameGateway;
        this.playerGateway = playerGateway;
        this.createBattleUseCase = createBattleUseCase;
    }

    @Transactional
    public List<Ranking> execute(){
        var games = gameGateway.findAllGames();
        var playersId = games.stream().map(game -> game.getPlayer().getId()).distinct().toList();
        var mapPlayersPoints = new HashMap<Player, BigDecimal>();
        playersId.forEach(playerId -> {
            var gamesFiltered = games.stream()
                    .filter(game -> game.getPlayer().getId().longValue() == playerId.longValue()).toList();
            var player = gamesFiltered.get(0).getPlayer();
            var numberOfGames = gamesFiltered.size();
            var numberOfBattles = gamesFiltered.stream()
                    .map(game -> game.getCountWon() + game.getCountLost()).reduce(Long::sum).orElse(0L);
            var numberOfBattlesWon = gamesFiltered.stream()
                    .map(Game::getCountWon).reduce(Long::sum).orElse(0L);
            var points = new BigDecimal(numberOfBattlesWon)
                    .divide(new BigDecimal(numberOfBattles), SCALE, RoundingMode.FLOOR)
                    .multiply(new BigDecimal(numberOfGames));
            mapPlayersPoints.put(player, points);
        });
        return Ranking.getRanking(mapPlayersPoints);
    }

    private void validateGameInProgress(String playerLogin){
        var gamesInProgress = gameGateway.findGamesByPlayerLoginAndGameStatus(playerLogin, GameStatus.STARTED);
        if(!gamesInProgress.isEmpty()){
            throw new GameInProgressException();
        }
    }

}
