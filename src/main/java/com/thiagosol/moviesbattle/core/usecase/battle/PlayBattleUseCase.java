package com.thiagosol.moviesbattle.core.usecase.battle;

import com.thiagosol.moviesbattle.core.domain.Battle;
import com.thiagosol.moviesbattle.core.domain.Game;
import com.thiagosol.moviesbattle.core.domain.Movie;
import com.thiagosol.moviesbattle.core.domain.Rating;
import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import com.thiagosol.moviesbattle.core.exception.BattleNotFoundException;
import com.thiagosol.moviesbattle.core.exception.GameInProgressNotFoundException;
import com.thiagosol.moviesbattle.core.exception.MovieNotFoundException;
import com.thiagosol.moviesbattle.core.gateway.GameGateway;
import com.thiagosol.moviesbattle.core.gateway.MovieGateway;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.List;

@Service
public class PlayBattleUseCase {

    private final MovieGateway movieGateway;
    private final GameGateway gameGateway;
    private final CreateBattleUseCase createBattleService;

    public PlayBattleUseCase(MovieGateway movieGateway, GameGateway gameGateway, CreateBattleUseCase createBattleService) {
        this.movieGateway = movieGateway;
        this.gameGateway = gameGateway;
        this.createBattleService = createBattleService;
    }

    @Transactional
    public Battle execute(String playerLogin, String playImdb){
        var gameInProgress = gameGateway.findGamesByPlayerLoginAndGameStatus(playerLogin, GameStatus.STARTED)
                .stream().findFirst().orElseThrow(GameInProgressNotFoundException::new);
        var battle = validateAndGetBattle(playImdb, gameInProgress);
        var betterMovie = betterMovie(battle.getMovieA(), battle.getMovieB());
        battle.play(playImdb, betterMovie);
        gameInProgress.update();

        if(gameInProgress.getStatus() == GameStatus.STARTED){
            var newBattle = createBattleService.execute(gameInProgress);
            gameInProgress.addBattle(newBattle);
        }

        gameGateway.saveGame(gameInProgress);

        return battle;
    }

    private Movie betterMovie(Movie movieA, Movie movieB){
        var moviesRating = movieGateway.getRatings(movieA, movieB);
        var ratingsA = getRatingByImdb(movieA.getImdb(), moviesRating);
        var ratingsB = getRatingByImdb(movieB.getImdb(), moviesRating);
        var scoreA = ratingsA.getAggregateRating().multiply(BigDecimal.valueOf(ratingsA.getVoteCount()));
        var scoreB = ratingsB.getAggregateRating().multiply(BigDecimal.valueOf(ratingsB.getVoteCount()));
        return scoreA.compareTo(scoreB) > 0 ? movieA : movieB;
    }

    private Rating getRatingByImdb(String imdb, List<Rating> moviesRating){
        return moviesRating.stream()
                .filter(movieRating -> movieRating.getMovie().getImdb().equals(imdb))
                .findFirst().orElseThrow(MovieNotFoundException::new);
    }

    private Battle validateAndGetBattle(String playImdbId, Game gameInProgress) {
        var battle = gameInProgress.getBattles().stream()
                .filter(battleFind -> battleFind.getStatus() == BattleStatus.WAITING)
                .findFirst()
                .orElseThrow(BattleNotFoundException::new);
        if(!battle.getMovieA().getImdb().equals(playImdbId) && !battle.getMovieB().getImdb().equals(playImdbId)){
            throw new MovieNotFoundException();
        }
        return battle;
    }

}
