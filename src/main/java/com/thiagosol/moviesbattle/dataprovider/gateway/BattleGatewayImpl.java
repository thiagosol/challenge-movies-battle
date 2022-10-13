package com.thiagosol.moviesbattle.dataprovider.gateway;

import com.thiagosol.moviesbattle.core.domain.Battle;
import com.thiagosol.moviesbattle.core.domain.enums.BattleStatus;
import com.thiagosol.moviesbattle.core.domain.enums.GameStatus;
import com.thiagosol.moviesbattle.core.gateway.BattleGateway;
import com.thiagosol.moviesbattle.dataprovider.model.BattleModel;
import com.thiagosol.moviesbattle.dataprovider.model.GameModel;
import com.thiagosol.moviesbattle.dataprovider.repository.BattleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BattleGatewayImpl implements BattleGateway {

    private final BattleRepository battleRepository;

    public BattleGatewayImpl(BattleRepository battleRepository) {
        this.battleRepository = battleRepository;
    }

    @Override
    public Battle saveBattle(Battle battle) {
        return battleRepository.save(new BattleModel(battle, new GameModel(battle.getGame()))).toBattle();
    }

    @Override
    public List<Battle> findBattlesByPlayerLogin(String login) {
        return battleRepository.findByGamePlayerLogin(login).stream().map(BattleModel::toBattle).toList();
    }

    @Override
    public List<Battle> findBattlesByPlayerLoginAndBattleStatusAndGameStatus(String login, BattleStatus battleStatus, GameStatus gameStatus) {
        return battleRepository.findByGamePlayerLoginAndGameStatusAndStatus(login, gameStatus, battleStatus)
                .stream().map(BattleModel::toBattle).toList();
    }
}
