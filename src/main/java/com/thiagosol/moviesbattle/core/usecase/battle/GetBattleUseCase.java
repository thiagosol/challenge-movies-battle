package com.thiagosol.moviesbattle.core.usecase.battle;

import com.thiagosol.moviesbattle.core.domain.Battle;
import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import com.thiagosol.moviesbattle.core.exception.BattleNotFoundException;
import com.thiagosol.moviesbattle.core.gateway.BattleGateway;
import org.springframework.stereotype.Service;

@Service
public class GetBattleUseCase {

    private final BattleGateway battleGateway;

    public GetBattleUseCase(BattleGateway battleGateway) {
        this.battleGateway = battleGateway;
    }

    public Battle execute(String playerLogin){
        var battleWaiting = battleGateway
                .findBattlesByPlayerLoginAndBattleStatusAndGameStatus(playerLogin, BattleStatus.WAITING, GameStatus.STARTED);
        return battleWaiting.stream().findFirst().orElseThrow(BattleNotFoundException::new);
    }

}
