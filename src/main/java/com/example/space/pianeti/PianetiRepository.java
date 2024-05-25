package com.example.space.pianeti;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PianetiRepository extends JpaRepository<Pianeta, Long> {
}
