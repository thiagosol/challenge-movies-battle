package com.thiagosol.moviesbattle.core.domain;

import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {

    private static final int SCALE = 2;
    private Long id;
    private GameStatus status;

    private Player player;

    private List<Battle> battles;

    private Long countWon;

    private Long countLost;

    private BigDecimal rating;

    public Game(Long id, GameStatus status, Player player, List<Battle> battles, Long countWon, Long countLost, BigDecimal rating) {
        this.id = id;
        this.status = status;
        this.player = player;
        this.battles = battles;
        this.countWon = countWon;
        this.countLost = countLost;
        this.rating = rating;
    }

    public Game(Player player) {
        this.status = GameStatus.STARTED;
        this.player = player;
        this.countWon = 0L;
        this.countLost = 0L;
        this.rating = BigDecimal.ZERO;
        this.battles = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void addBattle(Battle battle) {
        this.battles.add(battle);
    }

    public GameStatus getStatus() {
        return status;
    }

    public Player getPlayer() {
        return player;
    }

    public List<Battle> getBattles() {
        return Collections.unmodifiableList(battles);
    }

    public Long getCountLost() {
        return countLost;
    }

    public Long getCountWon() {
        return countWon;
    }

    public BigDecimal getRating() {
        return rating;
    }

    public void end() {
        this.status = GameStatus.FINISHED;
    }

    public void update() {
        this.countWon = getBattles().stream().filter(battleModel -> battleModel.getStatus() == BattleStatus.WON).count();
        this.countLost = getBattles().stream().filter(battleModel -> battleModel.getStatus() == BattleStatus.LOST).count();
        this.rating = new BigDecimal("100")
                .multiply(new BigDecimal(this.countWon))
                .divide(new BigDecimal(getBattles().size()), SCALE, RoundingMode.FLOOR);
        if(countLost >= 3){
            this.end();
        }
    }
}
