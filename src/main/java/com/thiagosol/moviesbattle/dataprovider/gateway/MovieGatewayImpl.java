package com.thiagosol.moviesbattle.dataprovider.gateway;

import com.thiagosol.moviesbattle.core.domain.Movie;
import com.thiagosol.moviesbattle.core.domain.Rating;
import com.thiagosol.moviesbattle.core.gateway.MovieGateway;
import com.thiagosol.moviesbattle.dataprovider.client.MovieClient;
import com.thiagosol.moviesbattle.dataprovider.client.dto.MovieResponseDTO;
import com.thiagosol.moviesbattle.dataprovider.model.MovieModel;
import com.thiagosol.moviesbattle.dataprovider.repository.MovieRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class MovieGatewayImpl implements MovieGateway {

    private final static int LIMIT = 50;
    private final MovieClient movieClient;
    private final MovieRepository movieRepository;

    public MovieGatewayImpl(MovieClient movieClient, MovieRepository movieRepository) {
        this.movieClient = movieClient;
        this.movieRepository = movieRepository;
    }

    @Override
    public void importNewMovies(Integer attempt) {
        var moviesResponse = movieClient.getMovies(Objects.isNull(attempt) ? 1 : attempt, LIMIT);
        var moviesModel = moviesResponse.getResults().stream()
                .map(MovieResponseDTO::toMovieModel)
                .toList();
        movieRepository.saveAll(moviesModel);
    }

    @Override
    public long moviesCount() {
        return movieRepository.count();
    }

    @Override
    public List<Movie> findAllMoviesByPagination(int page, int limit) {
        return movieRepository.findAll(Pageable.ofSize(limit).withPage(page))
                .stream().map(MovieModel::toMovie).toList();
    }

    @Override
    public List<Rating> getRatings(Movie movieA, Movie movieB) {
        return movieClient.getRating(movieA.getImdb(), movieB.getImdb()).getResults()
                .stream().map(ratingDTO -> ratingDTO.toRating(List.of(movieA, movieB))).toList();
    }
}
