package com.thiagosol.moviesbattle.entrypoint.contract;

import com.thiagosol.moviesbattle.entrypoint.dto.BattleResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.security.Principal;

@Tag(name = "Battle", description = "Play and list current battle.")
public interface BattleControllerApi {

    @Operation(summary = "Play the battle, perform the choice of the best movie.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BattleResponseDTO.class)))
    })
    ResponseEntity<BattleResponseDTO> playCurrentBattle(Principal principal, String imdbId);

    @Operation(summary = "Battle in progress information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BattleResponseDTO.class)))
    })
    ResponseEntity<BattleResponseDTO> currentBattle(Principal principal);
}