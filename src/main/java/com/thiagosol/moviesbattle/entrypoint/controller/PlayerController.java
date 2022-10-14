package com.thiagosol.moviesbattle.entrypoint.controller;

import com.thiagosol.moviesbattle.core.usecase.auth.AuthenticatePlayerUseCase;
import com.thiagosol.moviesbattle.entrypoint.contract.PlayerControllerApi;
import com.thiagosol.moviesbattle.entrypoint.dto.PlayerLoginRequestDTO;
import com.thiagosol.moviesbattle.entrypoint.dto.PlayerTokenResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("players")
public class PlayerController implements PlayerControllerApi {

    private final AuthenticatePlayerUseCase authenticatePlayerService;

    public PlayerController(AuthenticatePlayerUseCase authenticatePlayerUseCase) {
        this.authenticatePlayerService = authenticatePlayerUseCase;
    }

    @PostMapping("login")
    public ResponseEntity<PlayerTokenResponseDTO> login(@RequestBody PlayerLoginRequestDTO playerLoginDTO) {
        var token = authenticatePlayerService.authenticate(playerLoginDTO.toPlayer());
        return ResponseEntity.ok(new PlayerTokenResponseDTO(token));
    }
}
