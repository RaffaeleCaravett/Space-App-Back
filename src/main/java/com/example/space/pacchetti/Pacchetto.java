package com.example.space.pacchetti;

import com.example.space.pdf.Pdf;
import com.example.space.pianeti.Pianeta;
import com.example.space.prenotazioni.Prenotazione;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;

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
    private double prezzo;
    private int posti;
    private LocalDate da;
    private LocalDate a;
    @ManyToMany(mappedBy = "pacchettos")
    @JsonIgnore
    private List<Prenotazione> prenotaziones;
    @ManyToMany
    @JoinTable(name="pacchetto_pianeta",
    joinColumns = @JoinColumn(name = "pacchetto_id"),
    inverseJoinColumns = @JoinColumn(name = "pianeta_id"))
    private List<Pianeta> pianetas;
    @OneToMany(mappedBy = "pacchetto")
    @JsonIgnore
    private List<Pdf> pdfList;
}
