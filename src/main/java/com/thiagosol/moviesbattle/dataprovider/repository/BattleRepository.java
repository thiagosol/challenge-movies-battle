package com.thiagosol.moviesbattle.dataprovider.repository;

import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import com.thiagosol.moviesbattle.dataprovider.model.BattleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BattleRepository extends JpaRepository<BattleModel, Long> {

    List<BattleModel> findByGamePlayerLogin(String playerLogin);
    List<BattleModel> findByGamePlayerLoginAndGameStatusAndStatus(String playerLogin, GameStatus gameStatus, BattleStatus battleStatus);

}
