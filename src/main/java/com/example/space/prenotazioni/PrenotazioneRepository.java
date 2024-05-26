package com.example.space.prenotazioni;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrenotazioneRepository extends JpaRepository<Prenotazione,Long> {
    List<Prenotazione> findByPacchetto_Id(long id);
}
