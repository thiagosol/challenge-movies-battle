package com.thiagosol.moviesbattle.core.gateway;

import com.thiagosol.moviesbattle.core.domain.Movie;
import com.thiagosol.moviesbattle.core.domain.Rating;

import java.util.List;

public interface MovieGateway {

    void importNewMovies(Integer attempt);

    long moviesCount();

    List<Movie> findAllMoviesByPagination(int page, int limit);

    List<Rating> getRatings(Movie movieA, Movie movieB);
}
