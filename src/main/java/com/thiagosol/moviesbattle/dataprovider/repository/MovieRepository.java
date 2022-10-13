package com.thiagosol.moviesbattle.dataprovider.repository;

import com.thiagosol.moviesbattle.dataprovider.model.MovieModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<MovieModel, String> {
}
