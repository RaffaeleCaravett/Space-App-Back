package com.example.space.pacchetti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PacchettoRepository extends JpaRepository<Pacchetto,Long> {
    List<Pacchetto> findByPianetas_Id(long id);

    List<Pacchetto> findByPrezzoBetween(double first,double second);

    List<Pacchetto> findByDaGreaterThanEqualAndALessThanEqual(LocalDate first, LocalDate second);

    List<Pacchetto> findByPrezzoAndPostiAndDaAndAAndPianetas_Id(double prezzo, int posti,LocalDate da, LocalDate a, long pianeta_id);

List<Pacchetto> findByPianetas_IdAndPrezzoBetween(long id,double first, double second);

List<Pacchetto> findByPianetas_IdAndDaGreaterThanEqualAndALessThanEqual(long id , LocalDate da, LocalDate a);
    List<Pacchetto> findByPianetas_IdAndPrezzoBetweenAndDaGreaterThanEqualAndALessThanEqual(long id,double first, double second, LocalDate da, LocalDate a);
    List<Pacchetto> findByPrezzoBetweenAndDaGreaterThanEqualAndALessThanEqual(double first, double second, LocalDate da, LocalDate a);
}
