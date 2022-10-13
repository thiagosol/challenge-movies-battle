package com.thiagosol.moviesbattle.dataprovider.client.dto;

import com.thiagosol.moviesbattle.core.domain.Movie;
import com.thiagosol.moviesbattle.core.domain.Rating;
import com.thiagosol.moviesbattle.core.exception.MovieNotFoundException;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

public record MovieRatingResponseDTO(String id, RatingSummary ratingsSummary) {

    public record RatingSummary (BigDecimal aggregateRating, long voteCount) {
        @Override
        public BigDecimal aggregateRating() {
            return Objects.nonNull(aggregateRating) ? aggregateRating : BigDecimal.ZERO;
        }
    }

    public Rating toRating(List<Movie> movies){
        var movieFind = movies.stream()
                .filter(movie -> movie.getImdb().equals(id()))
                .findFirst()
                .orElseThrow(MovieNotFoundException::new);
        return new Rating(movieFind, ratingsSummary().aggregateRating(), ratingsSummary().voteCount());
    }
}
