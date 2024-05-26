package com.example.space.pacchetti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PacchettoRepository extends JpaRepository<Pacchetto,Long> {
}
