package com.thiagosol.moviesbattle.dataprovider.model;

import com.thiagosol.moviesbattle.core.domain.Movie;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name="MOVIE")
public class MovieModel {

    @Id
    private String imdb;

    private String title;

    private Long releaseYear;

    private LocalDateTime createdAt;

    public MovieModel(){}
    public MovieModel(Movie movie) {
        this.imdb = movie.getImdb();
        this.title = movie.getTitle();
        this.releaseYear = movie.getReleaseYear();
        this.createdAt = LocalDateTime.now();
    }

    public MovieModel(String imdb, String title, Long releaseYear) {
        this.imdb = imdb;
        this.title = title;
        this.releaseYear = releaseYear;
        this.createdAt = LocalDateTime.now();
    }

    public Movie toMovie(){
        return new Movie(imdb, title, releaseYear);
    }
}
