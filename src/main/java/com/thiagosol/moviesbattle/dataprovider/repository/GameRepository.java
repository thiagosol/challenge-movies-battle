package com.thiagosol.moviesbattle.dataprovider.repository;

import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import com.thiagosol.moviesbattle.dataprovider.model.GameModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GameRepository extends JpaRepository<GameModel, Long> {

    Optional<GameModel> findByPlayerLoginAndStatus(String playerLogin, GameStatus gameStatus);
}
