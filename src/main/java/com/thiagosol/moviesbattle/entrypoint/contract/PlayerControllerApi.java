package com.thiagosol.moviesbattle.entrypoint.contract;

import com.thiagosol.moviesbattle.entrypoint.dto.PlayerLoginRequestDTO;
import com.thiagosol.moviesbattle.entrypoint.dto.PlayerTokenResponseDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "Player", description = "Player operations.")
public interface PlayerControllerApi {

    @Operation(summary = "Player login.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "400", description = "Bad Request",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = PlayerLoginRequestDTO.class)))
    })
    ResponseEntity<PlayerTokenResponseDTO> login(@RequestBody PlayerLoginRequestDTO playerLoginDTO);

}