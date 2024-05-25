package com.example.space.prenotazioni;

import com.example.space.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

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
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToMany(mappedBy = "prenotazione_id")
    @JoinTable(name = "prenotazione_pacchetto",
            joinColumns = @JoinColumn(name = "prenotazione_id"),
            inverseJoinColumns = @JoinColumn(name = "pacchetto_id"))
private List<Pacchetto> pacchettos;
    
}
