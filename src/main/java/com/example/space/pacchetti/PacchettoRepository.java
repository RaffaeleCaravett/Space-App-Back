package com.example.space.pacchetti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PacchettoRepository extends JpaRepository<Pacchetto,Long> {
    List<Pacchetto> findByPiante_Id(long id);

    List<Pacchetto> findByPrezzoBetween(double first,double second);
}
