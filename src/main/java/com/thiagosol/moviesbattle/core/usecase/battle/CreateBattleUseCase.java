package com.thiagosol.moviesbattle.core.usecase.battle;

import com.thiagosol.moviesbattle.core.domain.Battle;
import com.thiagosol.moviesbattle.core.domain.Game;
import com.thiagosol.moviesbattle.core.exception.CreateBattleErrorException;
import com.thiagosol.moviesbattle.core.gateway.BattleGateway;
import com.thiagosol.moviesbattle.core.gateway.MovieGateway;
import javafx.util.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Component
public class CreateBattleUseCase {

    private final static int LIMIT = 5;

    private final static int MAXIMUM_ATTEMPTS = 5;

    private final MovieGateway movieGateway;

    private final BattleGateway battleGateway;

    public CreateBattleUseCase(MovieGateway movieGateway, BattleGateway battleGateway) {
        this.movieGateway = movieGateway;
        this.battleGateway = battleGateway;
    }

    public Battle execute(Game game){
        var battlesOccurred = battleGateway.findBattlesByPlayerLogin(game.getPlayer().getLogin());
        var battleOptional = generateBattle(game, battlesOccurred);
        var attempts = 0;

        while(battleOptional.isEmpty() && attempts < MAXIMUM_ATTEMPTS){
            attempts++;
            movieGateway.importNewMovies(attempts);
            battleOptional = generateBattle(game, battlesOccurred);
        }
        return battleOptional.orElseThrow(CreateBattleErrorException::new);
    }

    private Optional<Battle> generateBattle(Game game, List<Battle> battlesOccurred) {
        var totalPage = (movieGateway.moviesCount() / LIMIT);
        var pagesRandom = generateSequenceRandom(totalPage);
        List<Battle> possibleBattles = new ArrayList<>();

        for(int i=0; i<pagesRandom.size(); i++){
            var pageA = pagesRandom.get(i);
            var pageB = pagesRandom.get(generateRandomNumber(pagesRandom.size() - 1));
            var moviesA = movieGateway.findAllMoviesByPagination(pageA, LIMIT);
            var moviesB = movieGateway.findAllMoviesByPagination(pageB, LIMIT);
            moviesA.forEach((movieA) -> moviesB.forEach(movieB -> {
                    var battle = new Battle(game, movieA, movieB);
                    if(battlesOccurred.stream().noneMatch(battleOccurred -> battleOccurred.getBattleIdentifier().equals(battle.getBattleIdentifier()))
                    && !movieA.getImdb().equals(movieB.getImdb())){
                        possibleBattles.add(battle);
                    }
                }));
            if(!possibleBattles.isEmpty()) break;
        }
        int randomBattle = generateRandomNumber(possibleBattles.size() - 1);
        return possibleBattles.isEmpty() ? Optional.empty() : Optional.of(possibleBattles.get(randomBattle));
    }

    private List<Integer> generateSequenceRandom(long totalPage){
        return Stream.iterate(1, n -> n + 1)
                .limit(totalPage)
                .map(number -> new Pair<>(number, Math.random()))
                .sorted(Comparator.comparingDouble(Pair::getValue))
                .map(Pair::getKey)
                .toList();
    }

    private Integer generateRandomNumber(int max){
        return (int) Math.round(Math.random() * max);
    }
}
