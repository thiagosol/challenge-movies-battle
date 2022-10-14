package com.thiagosol.moviesbattle.entrypoint.contract;

import com.thiagosol.moviesbattle.entrypoint.dto.BattleResponseDTO;
import com.thiagosol.moviesbattle.entrypoint.dto.GameResponseDTO;
import com.thiagosol.moviesbattle.entrypoint.dto.RankingResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.List;

@Tag(name = "Game", description = "Start game, end Game and list ranking.")
public interface GameControllerApi {

    @Operation(summary = "Start new game.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Game started"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BattleResponseDTO.class)))
    })
    ResponseEntity<GameResponseDTO> start(Principal principal);

    @Operation(summary = "End Game.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "End Game"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE))
    })
    ResponseEntity<Void> end(Principal principal);

    @Operation(summary = "Ranking information.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RankingResponseDTO.class)))
    })
    ResponseEntity<List<RankingResponseDTO>> ranking();
}