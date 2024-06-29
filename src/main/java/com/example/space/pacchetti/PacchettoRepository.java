package com.example.space.pacchetti;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PacchettoRepository extends JpaRepository<Pacchetto,Long> {
    List<Pacchetto> findByPianetas_Id(long id);

    Page<Pacchetto> findByPrezzoBetween(Pageable pageable,double first,double second);

    Page<Pacchetto> findByDaGreaterThanEqualAndALessThanEqual(Pageable pageable,LocalDate first, LocalDate second);

    List<Pacchetto> findByPrezzoAndPostiAndDaAndAAndPianetas_Id(double prezzo, int posti,LocalDate da, LocalDate a, long pianeta_id);

Page<Pacchetto> findByIdAndPrezzoBetween(Pageable pageable,long id,double first, double second);

Page<Pacchetto> findByIdAndDaGreaterThanEqualAndALessThanEqual(Pageable pageable,long id , LocalDate da, LocalDate a);
    Page<Pacchetto> findByIdAndPrezzoBetweenAndDaGreaterThanEqualAndALessThanEqual(Pageable pageable,long id,double first, double second, LocalDate da, LocalDate a);
    Page<Pacchetto> findByPrezzoBetweenAndDaGreaterThanEqualAndALessThanEqual(Pageable pageable,double first, double second, LocalDate da, LocalDate a);

    Page<Pacchetto> findById(Pageable pageable,long id);
}
