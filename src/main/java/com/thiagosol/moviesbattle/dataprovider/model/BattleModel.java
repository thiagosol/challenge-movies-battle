package com.thiagosol.moviesbattle.dataprovider.model;

import com.thiagosol.moviesbattle.core.domain.Battle;
import com.thiagosol.moviesbattle.core.domain.Game;
import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="BATTLE")
public class BattleModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="game_id")
    private GameModel game;

    @ManyToOne
    @JoinColumn(name="movie_a_id", nullable=false)
    private MovieModel movieA;

    @ManyToOne
    @JoinColumn(name="movie_b_id", nullable=false)
    private MovieModel movieB;

    @Enumerated(EnumType.STRING)
    private BattleStatus status;

    private LocalDateTime createdAt;

    public BattleModel(){}
    public BattleModel(Battle battle, GameModel game) {
        this.id = battle.getId();
        this.game = game;
        this.movieA = new MovieModel(battle.getMovieA());
        this.movieB = new MovieModel(battle.getMovieB());
        this.status = battle.getStatus();
        this.createdAt = LocalDateTime.now();
    }

    public Battle toBattle(){
        return toBattle(null);
    }
    public Battle toBattle(Game game){
        return new Battle(id, game, movieA.toMovie(), movieB.toMovie(), status);
    }

}
