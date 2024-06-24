package com.example.space.pianeti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PianetiRepository extends JpaRepository<Pianeta, Long> {

    List<Pianeta> findByNomeAndGalassia(String nome, Enum galassia);

    Optional<Pianeta> findByIdAndNomeAndGalassia(Long id,String nome, Enum galassia);
}
