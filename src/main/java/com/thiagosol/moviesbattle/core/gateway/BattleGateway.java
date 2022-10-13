package com.thiagosol.moviesbattle.core.gateway;

import com.thiagosol.moviesbattle.core.domain.Battle;
import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;

import java.util.List;

public interface BattleGateway {

    Battle saveBattle(Battle battle);
    List<Battle> findBattlesByPlayerLogin(String login);
    List<Battle> findBattlesByPlayerLoginAndBattleStatusAndGameStatus(String login, BattleStatus battleStatus, GameStatus gameStatus);

}
