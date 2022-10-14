package com.thiagosol.moviesbattle.entrypoint.controller;

import com.thiagosol.moviesbattle.core.usecase.game.EndGameUseCase;
import com.thiagosol.moviesbattle.core.usecase.game.GetRankingUseCase;
import com.thiagosol.moviesbattle.core.usecase.game.StartNewGameUseCase;
import com.thiagosol.moviesbattle.entrypoint.contract.GameControllerApi;
import com.thiagosol.moviesbattle.entrypoint.dto.GameResponseDTO;
import com.thiagosol.moviesbattle.entrypoint.dto.RankingResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("games")
public class GameController implements GameControllerApi {

    private final StartNewGameUseCase startNewGameUseCase;
    private final EndGameUseCase endGameUseCase;

    private final GetRankingUseCase getRankingUseCase;

    public GameController(StartNewGameUseCase startNewGameUseCase, EndGameUseCase endGameUseCase, GetRankingUseCase getRankingUseCase) {
        this.startNewGameUseCase = startNewGameUseCase;
        this.endGameUseCase = endGameUseCase;
        this.getRankingUseCase = getRankingUseCase;
    }

    @PostMapping("start")
    public ResponseEntity<GameResponseDTO> start(Principal principal) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new GameResponseDTO(startNewGameUseCase.execute(principal.getName())));
    }

    @PutMapping("end")
    public ResponseEntity<Void> end(Principal principal) {
        endGameUseCase.execute(principal.getName());
        return ResponseEntity.ok().build();
    }

    @GetMapping("ranking")
    public ResponseEntity<List<RankingResponseDTO>> ranking() {
        return ResponseEntity.ok(getRankingUseCase.execute().stream().map(RankingResponseDTO::new).toList());
    }

}
