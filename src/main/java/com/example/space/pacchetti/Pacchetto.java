package com.example.space.pacchetti;

import com.example.space.prenotazioni.Prenotazione;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "pacchetto")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pacchetto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToMany(mappedBy = "pacchettos")
    @JsonIgnore
    private List<Prenotazione> prenotaziones;
}
