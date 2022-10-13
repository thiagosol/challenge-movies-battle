package com.thiagosol.moviesbattle.dataprovider.model;

import com.thiagosol.moviesbattle.core.domain.Game;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="GAME")
public class GameModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private GameStatus status;
    @ManyToOne
    @JoinColumn(name="player_id", nullable=false)
    private PlayerModel player;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL)
    private List<BattleModel> battles;

    private Long countWon;

    private Long countLost;

    private BigDecimal rating;

    private LocalDateTime createdAt;

    public GameModel(){}
    public GameModel(Game game) {
        this.id = game.getId();
        this.status = game.getStatus();
        this.player = new PlayerModel(game.getPlayer());
        this.battles = game.getBattles().stream().map(battle -> new BattleModel(battle, this)).toList();
        this.createdAt = LocalDateTime.now();
        this.countWon = game.getCountWon();
        this.countLost = game.getCountLost();
        this.rating = game.getRating();
    }

    public Game toGame(){
        var game = new Game(id, status, player.toPlayer(), new ArrayList<>(), countWon, countLost, rating);
        battles.forEach(battle -> game.addBattle(battle.toBattle(game)));
        return game;
    }

}
