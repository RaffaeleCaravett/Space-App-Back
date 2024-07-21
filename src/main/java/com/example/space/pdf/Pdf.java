package com.example.space.pdf;

import com.example.space.pacchetti.Pacchetto;
import com.example.space.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "pdf")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pdf {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "package_id")
    private Pacchetto pacchetto;
}
