package com.thiagosol.moviesbattle.core.domain;

import java.math.BigDecimal;

public class Rating {

    private Movie movie;
    private BigDecimal aggregateRating;
    private long voteCount;

    public Rating(Movie movie, BigDecimal aggregateRating, long voteCount) {
        this.movie = movie;
        this.aggregateRating = aggregateRating;
        this.voteCount = voteCount;
    }

    public Movie getMovie() {
        return movie;
    }

    public BigDecimal getAggregateRating() {
        return aggregateRating;
    }

    public long getVoteCount() {
        return voteCount;
    }
}
