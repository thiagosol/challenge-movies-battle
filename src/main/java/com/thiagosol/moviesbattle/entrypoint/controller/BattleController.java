package com.thiagosol.moviesbattle.entrypoint.controller;

import com.thiagosol.moviesbattle.core.usecase.battle.GetBattleUseCase;
import com.thiagosol.moviesbattle.core.usecase.battle.PlayBattleUseCase;
import com.thiagosol.moviesbattle.entrypoint.contract.BattleControllerApi;
import com.thiagosol.moviesbattle.entrypoint.dto.BattleResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("battles")
public class BattleController implements BattleControllerApi {
    private final GetBattleUseCase getBattleUseCase;
    private final PlayBattleUseCase playBattleUseCase;

    public BattleController(GetBattleUseCase getBattleUseCase, PlayBattleUseCase playBattleUseCase) {
        this.getBattleUseCase = getBattleUseCase;
        this.playBattleUseCase = playBattleUseCase;
    }

    @PostMapping("current/play/{imdbId}")
    public ResponseEntity<BattleResponseDTO> playCurrentBattle(Principal principal, @PathVariable String imdbId) {
        return ResponseEntity.ok(new BattleResponseDTO(playBattleUseCase.execute(principal.getName(), imdbId)));
    }

    @GetMapping("current")
    public ResponseEntity<BattleResponseDTO> currentBattle(Principal principal) {
        return ResponseEntity.ok(new BattleResponseDTO(getBattleUseCase.execute(principal.getName())));
    }

}
