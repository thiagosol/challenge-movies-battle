package com.thiagosol.moviesbattle.core.domain;

public class Movie {

    private String imdb;

    private String title;

    private Long releaseYear;

    public Movie(String imdb, String title, Long releaseYear) {
        this.imdb = imdb;
        this.title = title;
        this.releaseYear = releaseYear;
    }

    public String getImdb() {
        return imdb;
    }

    public String getTitle() {
        return title;
    }

    public Long getReleaseYear() {
        return releaseYear;
    }
}
