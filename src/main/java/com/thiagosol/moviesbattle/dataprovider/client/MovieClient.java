package com.thiagosol.moviesbattle.dataprovider.client;

import com.thiagosol.moviesbattle.dataprovider.client.dto.MovieRatingResponseDTO;
import com.thiagosol.moviesbattle.dataprovider.client.dto.MovieResponseDTO;
import com.thiagosol.moviesbattle.dataprovider.client.dto.ResponseDTO;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "MovieClient", url = "${api.movie.url}")
@Headers({
        "Content-Type: application/json",
        "Accept: application/json"
})
public interface MovieClient {

    @GetMapping(value = "titles?info=mini_info&list=most_pop_movies&titleType=movie&page={page}&limit={limit}")
    ResponseDTO<MovieResponseDTO> getMovies(@PathVariable("page") Integer page,
                                            @PathVariable("limit") Integer limit);

    @GetMapping(value = "titles/x/titles-by-ids?info=rating&idsList[0]={movieA}&idsList[1]={movieB}")
    ResponseDTO<MovieRatingResponseDTO> getRating(@PathVariable("movieA") String movieA,
                                                  @PathVariable("movieB") String movieB);
}
