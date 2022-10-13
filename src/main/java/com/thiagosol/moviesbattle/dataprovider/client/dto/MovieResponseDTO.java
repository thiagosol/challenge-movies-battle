package com.thiagosol.moviesbattle.dataprovider.client.dto;

import com.thiagosol.moviesbattle.dataprovider.model.MovieModel;

import java.util.Objects;

public record MovieResponseDTO(String id, TitleTextDTO titleText, YearRangeDTO releaseYear) {
    public MovieModel toMovieModel(){
        return new MovieModel(id, titleText.text(),
                Objects.nonNull(releaseYear) ? releaseYear.year() : null);
    }
    public record TitleTextDTO (String text) {}
    public record YearRangeDTO (Long year) {}
}