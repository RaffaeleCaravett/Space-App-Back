package com.example.space.pdf;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface PdfRepository extends JpaRepository<Pdf,Long> {
    Optional<Pdf> findByUser_IdAndPacchetto_Id(long user_id, long pacchetto_id);
}
