package com.thiagosol.moviesbattle.dataprovider.repository;

import com.thiagosol.moviesbattle.dataprovider.model.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<PlayerModel, Long> {

    Optional<PlayerModel> findByLogin(String login);
}
