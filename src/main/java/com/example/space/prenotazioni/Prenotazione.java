package com.example.space.prenotazioni;

import com.example.space.pacchetti.Pacchetto;
import com.example.space.user.User;
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
@Table(name = "table")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Prenotazione {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany
    @JoinTable(name = "prenotazione_pacchetto",
            joinColumns = @JoinColumn(name = "prenotazione_id"),
            inverseJoinColumns = @JoinColumn(name = "pacchetto_id"))
private List<Pacchetto> pacchettos;
    private LocalDate created_ad;

}
