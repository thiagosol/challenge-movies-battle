package com.thiagosol.moviesbattle.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.thiagosol.moviesbattle.config.WireMockConfig;
import com.thiagosol.moviesbattle.dataprovider.repository.GameRepository;
import com.thiagosol.moviesbattle.dataprovider.repository.MovieRepository;
import com.thiagosol.moviesbattle.entrypoint.dto.BattleResponseDTO;
import com.thiagosol.moviesbattle.entrypoint.dto.PlayerLoginRequestDTO;
import com.thiagosol.moviesbattle.mocks.MovieMocks;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.HashMap;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@EnableConfigurationProperties
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = { WireMockConfig.class })
public class MoviesBattleApplicationIntegrationTests {

    public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), MediaType.APPLICATION_JSON.getSubtype(), StandardCharsets.UTF_8);
    private static final String LOGIN_URL = "/players/login";
    private static final String START_GAME_URL = "/games/start";
    private static final String END_GAME_URL = "/games/end";
    private static final String RANKING_GAME_URL = "/games/ranking";
    private static final String CURRENT_BATTLE_URL = "/battles/current";
    private static final String PLAY_BATTLE_URL = "/battles/current/play/{imdbId}";
    private final WireMockServer mockService;
    private final MockMvc mockMvc;
    private final ObjectMapper mapper;
    private final MovieRepository movieRepository;
    private final GameRepository gameRepository;
    @MockBean
    private Principal principal;

    @Autowired
    MoviesBattleApplicationIntegrationTests(WireMockServer mockService, WebApplicationContext context, MovieRepository movieRepository, GameRepository gameRepository) {
        this.mockService = mockService;
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        this.gameRepository = gameRepository;
        this.mapper = new ObjectMapper();
        this.movieRepository = movieRepository;
    }

    @BeforeEach
    void setUpBefore() throws Exception {
        Mockito.when(principal.getName()).thenReturn("player1");

        MovieMocks.setupMockMoviesSuccessResponse(mockService);
    }

    @AfterEach
    void setUptAfter(){
        mockService.resetMappings();
    }

    @Test
    void loginTest() throws Exception {
        mockMvc.perform(post(LOGIN_URL)
                        .contentType(APPLICATION_JSON_UTF8)
                        .content(mapper.writeValueAsString(new PlayerLoginRequestDTO("player1", "12345"))))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }

    @Test
    void startAndEndGameTest() throws Exception {

        mockMvc.perform(post(START_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("STARTED"))
                .andExpect(jsonPath("$.battles").exists());

        mockMvc.perform(put(END_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void startGameWhenOrPlayerNotExists() throws Exception {

        var principalPlayerNotExists = Mockito.mock(Principal.class);
        Mockito.when(principalPlayerNotExists.getName()).thenReturn("playerNotExists");

        mockMvc.perform(post(START_GAME_URL)
                        .principal(principalPlayerNotExists))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Player not found"));

    }

    @Test
    void startGameWhenMoviesNotExists() throws Exception {

        gameRepository.deleteAll();
        movieRepository.deleteAll();
        MovieMocks.setupMockMoviesEmptyResponse(mockService);

        mockMvc.perform(post(START_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Could not create battle try again later"));

    }

    @Test
    void startGameWhenThatGameAlreadyExistsTest() throws Exception {

        mockMvc.perform(post(START_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("STARTED"))
                .andExpect(jsonPath("$.battles").exists());

        mockMvc.perform(post(START_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("There is already a game in progress, list and continue the same in battle/current"));

        mockMvc.perform(put(END_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void endGameWhenThereIsNotGameInProgressTest() throws Exception {

        mockMvc.perform(put(END_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Game in progress not found, create a new one in games/start"));
    }

    @Test
    void currentBattleTest() throws Exception {

        mockMvc.perform(post(START_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("STARTED"))
                .andExpect(jsonPath("$.battles").exists());

        mockMvc.perform(get(CURRENT_BATTLE_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.movieA.imdb").exists())
                .andExpect(jsonPath("$.movieA.title").exists())
                .andExpect(jsonPath("$.movieA.releaseYear").exists())
                .andExpect(jsonPath("$.movieB.imdb").exists())
                .andExpect(jsonPath("$.movieB.title").exists())
                .andExpect(jsonPath("$.movieB.releaseYear").exists());

        mockMvc.perform(put(END_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void currentPlayBattleWhenNotExists() throws Exception {

        var principalPlayerNotExists = Mockito.mock(Principal.class);
        Mockito.when(principalPlayerNotExists.getName()).thenReturn("playerNotExists");

        mockMvc.perform(get(CURRENT_BATTLE_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("There are no battles, create a new game in games/start"));
    }

    @Test
    void playBattleWhenMovieNotExists() throws Exception {

        mockMvc.perform(post(START_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(post(PLAY_BATTLE_URL.replace("{imdbId}", "movieNotExists"))
                        .principal(principal))
                .andDo(print())
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("$.message").value("Movie imdb not found in active battle"));

        mockMvc.perform(put(END_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void playBattleTest() throws Exception {

        mockMvc.perform(post(START_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("STARTED"))
                .andExpect(jsonPath("$.battles").exists());

        var currentBattleResponse = mockMvc.perform(get(CURRENT_BATTLE_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").exists())
                .andExpect(jsonPath("$.movieA.imdb").exists())
                .andExpect(jsonPath("$.movieA.title").exists())
                .andExpect(jsonPath("$.movieA.releaseYear").exists())
                .andExpect(jsonPath("$.movieB.imdb").exists())
                .andExpect(jsonPath("$.movieB.title").exists())
                .andExpect(jsonPath("$.movieB.releaseYear").exists())
                .andReturn();

        var currentBattle = mapper.readValue(currentBattleResponse.getResponse().getContentAsString(), BattleResponseDTO.class);

        var hashMovies = new HashMap<String, String>();
        hashMovies.put("movieA", currentBattle.movieA().imdb());
        hashMovies.put("movieB", currentBattle.movieB().imdb());
        MovieMocks.setupMockRatingSuccessResponse(mockService, hashMovies);

        mockMvc.perform(post(PLAY_BATTLE_URL.replace("{imdbId}", currentBattle.movieA().imdb()))
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("WON"))
                .andExpect(jsonPath("$.movieA.imdb").exists())
                .andExpect(jsonPath("$.movieA.title").exists())
                .andExpect(jsonPath("$.movieA.releaseYear").exists())
                .andExpect(jsonPath("$.movieB.imdb").exists())
                .andExpect(jsonPath("$.movieB.title").exists())
                .andExpect(jsonPath("$.movieB.releaseYear").exists())
                .andReturn();

        mockMvc.perform(put(END_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk());

        mockMvc.perform(get(RANKING_GAME_URL)
                        .principal(principal))
                .andDo(print())
                .andExpect(status().isOk());

    }
}
